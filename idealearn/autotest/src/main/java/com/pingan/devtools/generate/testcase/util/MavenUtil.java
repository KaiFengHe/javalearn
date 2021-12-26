package com.pingan.devtools.generate.testcase.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MavenUtil {

    public static String getLocalRepoPath() {
        try {
            Runtime rt = Runtime.getRuntime();
            Process process = rt.exec("cmd /c mvn help:effective-settings");

            InputStreamReader reader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.indexOf("<localRepository") != -1) {
                    return line.substring(line.indexOf('>') + 1, line.lastIndexOf('<'));
                }
            }
            process.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
