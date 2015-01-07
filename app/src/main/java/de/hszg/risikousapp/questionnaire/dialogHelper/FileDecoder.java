package de.hszg.risikousapp.questionnaire.dialogHelper;

import android.app.Activity;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jens on 18.12.2014.
 */
public class FileDecoder extends Activity {

    private String file;

    public FileDecoder(String fileStream) {
        InputStream inputStream = null;
        byte[] bytes;
        byte[] buffer = new byte[5242880];
        int bytesRead = 0;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {

        }
        bytes = output.toByteArray();
        file = Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public String getFile() {
        return file;
    }
}
