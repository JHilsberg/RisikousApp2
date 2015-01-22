package de.hszg.risikousapp.questionnaire.dialogHelper;

import android.app.Activity;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Encodes a file selected by the user with Base64.
 */
public class FileDecoder extends Activity {

    private String fileBase64;

    public FileDecoder(String filePath) {
        File file = new File(filePath);
        FileInputStream fileInputStream = null ;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            Log.e("file", "Datei nicht gefunden");
        }
        byte[] bytes;
        byte[] buffer = new byte[5242880];
        int bytesRead = 0;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            Log.e("Kodierung", "Fehler bei der Kodierung", e);
        }
        bytes = output.toByteArray();
        fileBase64 = Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public String getFile() {
        return fileBase64;
    }
}
