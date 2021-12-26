package com.hekaifeng.autotest.utgen;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ModulesInfo {

        /*
            full paths of all source root folders.
            this is needed to calculate the Java class names from the .java file paths
        */
    private final Set<String> roots = new LinkedHashSet<>();
    private final Set<String> modulePaths = new LinkedHashSet<>();
    private final String projectDir;

    public ModulesInfo(Project project){
        for (Module module : ModuleManager.getInstance(project).getModules()) {
            for(VirtualFile sourceRoot : ModuleRootManager.getInstance(module).getSourceRoots()){
                String path = new File(sourceRoot.getCanonicalPath()).getAbsolutePath();
                roots.add(path);
            }

            String mp = getFolderLocation(module);
            if(mp != null) {
                modulePaths.add(mp);
            }
        }

        projectDir = new File(project.getBaseDir().getCanonicalPath()).getAbsolutePath(); //note: need "File" to avoid issues in Windows
    }

    public String getFolderLocation(Module module){
        ModuleRootManager rootManager = ModuleRootManager.getInstance(module);
        VirtualFile[] contentRoots = rootManager.getContentRoots(); //TODO check why IntelliJ does return an array here

        if(contentRoots==null || contentRoots.length==0){
            return null;
        }

        return new File(contentRoots[0].getCanonicalPath()).getAbsolutePath();
    }

    public boolean hasRoots(){
        return ! roots.isEmpty();
    }

    public String getSourceRootForFile(String path){
        for(String root : roots){
            if(path.startsWith(root)){
                return root;
            }
        }
        return null;
    }

    public Set<String> getIncludedSourceRoots(String path){
        Set<String> set = new HashSet<>();
        for(String root : roots){
            if(root.startsWith(path)){
                set.add(root);
            }
        }
        return set;
    }

    public Set<String> getModulePathsView(){
        return Collections.unmodifiableSet(modulePaths);
    }

    /**
     *
     * @param source
     * @return
     */
    public String getModuleFolder(String source){
        File file = new File(source);
        while(file != null){

            String path = file.getAbsolutePath();

            if(! path.startsWith(projectDir)){
                //return projectDir; //we went too up in the hierarchy
                return null;
            }

            if(file.isDirectory()){
                File pom = new File(file,"pom.xml");
                if(pom.exists()){
                    return path;
                }
                //with new check, maybe pom.xml is not needed any more
                if(modulePaths.contains(path)){
                    return path;
                }
            }

            file = file.getParentFile();
        }
        return projectDir;
    }
}