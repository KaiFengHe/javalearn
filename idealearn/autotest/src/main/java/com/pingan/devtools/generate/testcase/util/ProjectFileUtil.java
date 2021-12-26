package com.pingan.devtools.generate.testcase.util;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ProjectFileUtil {

    private final Set<String> roots = new LinkedHashSet<>();
    private final Set<String> modulePaths = new LinkedHashSet<>();
    private final String projectDir = "";

    public static void scanFolder(File file, Set<String> classes, String root) {
        for (File child : file.listFiles()) {
            if (child.isDirectory()) {
                scanFolder(child, classes, root);
            } else {
                String path = child.getAbsolutePath();
                if (path.endsWith(".java")) {
                    String name = getCUTName(path, root);
                    classes.add(name);
                }
            }
        }
    }

    private static String getCUTName(String path, String root) {
        String name = path.substring(root.length() + 1, path.length() - ".java".length());
        name = name.replace('/', '.'); //posix
        name = name.replace("\\", ".");  // windows
        final String s = ".src.main.java.";
        int index;
        if ((index = name.indexOf(s)) != -1) {
            name = name.substring(index + s.length());
        }
        return name;
    }

    public static String getSourceRootForFile(String path, String rootPath) {
        Set<String> roots = getAllRoots(rootPath);

        for (String root : roots) {
            if (path.startsWith(root)) {
                return root;
            }
        }
        return null;
    }

    public static Set<String> getAllRoots(String rootPath) {
        File file = new File(rootPath);
        List<File> fileList = new ArrayList<>();
        getAllFile(file, fileList);
        return fileList.stream().map(File::getAbsolutePath).collect(Collectors.toSet());
    }

    private static void getAllFile(File file, List<File> fileList) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File listFile : files) {
                    if (listFile.getAbsolutePath().endsWith("src\\main\\java")
                            || listFile.getAbsolutePath().endsWith("src\\test\\java")
                            || listFile.getAbsolutePath().endsWith("src\\main\\resources")
                            || listFile.getAbsolutePath().endsWith("src\\test\\resources")) {
                        fileList.add(listFile);
                    }
                    getAllFile(listFile, fileList);
                }
            }
        }
    }

    /**
     * 获取工程下所有modual
     *
     * @param projectPath
     * @return
     */
    public static Set<String> getModules(String projectPath) {
        Set<String> allRoots = getAllRoots(projectPath);
        Set<String> modules = new HashSet<>();
        for (String allRoot : allRoots) {
            if (allRoot.contains("\\src\\main\\java")) {
                String replace = allRoot.replace("\\src\\main\\java", "");
                modules.add(replace);
            }
        }
        return modules;
    }

    /**
     * @param source
     * @return
     */
    public String getModuleFolder(String source) {
        File file = new File(source);
        while (file != null) {

            String path = file.getAbsolutePath();

            if (!path.startsWith(projectDir)) {
                //return projectDir; //we went too up in the hierarchy
                return null;
            }

            if (file.isDirectory()) {
                File pom = new File(file, "pom.xml");
                if (pom.exists()) {
                    return path;
                }
                //with new check, maybe pom.xml is not needed any more
                if (modulePaths.contains(path)) {
                    return path;
                }
            }

            file = file.getParentFile();
        }
        return projectDir;
    }
}
