import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.fileapp.controller.FileController;
import com.example.fileapp.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FileController.class)
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());

        mockMvc.perform(multipart("/upload")
                .file(file)
                .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("File uploaded successfully"));
        
        verify(fileService, times(1)).saveFile(any(), anyString());
    }

    @Test
    public void testDownloadFile() throws Exception {
        String fileName = "test.txt";

        when(fileService.retrieveFile(fileName)).thenReturn("File content");

        mockMvc.perform(get("/download/{fileName}", fileName))
                .andExpect(status().isOk())
                .andExpect(content().string("File content"));
        
        verify(fileService, times(1)).retrieveFile(fileName);
    }

    @Test
    public void testUploadFileInvalidEmail() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());

        mockMvc.perform(multipart("/upload")
                .file(file)
                .param("email", "invalid-email"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid email address"));
        
        verify(fileService, never()).saveFile(any(), anyString());
    }
}