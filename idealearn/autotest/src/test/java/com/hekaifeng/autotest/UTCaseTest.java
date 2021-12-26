package com.hekaifeng.autotest;

import com.pingan.devtools.generate.testcase.TestProject;
import com.pingan.devtools.generate.testcase.model.TestProjectInfo;
import com.pingan.devtools.generate.testcase.util.MavenUtil;

import com.pingan.devtools.generate.testcase.util.ProjectFileUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class UTCaseTest {

    @Test
    public void testUTCase() throws Exception {
        String projectPath = System.getProperty("user.dir");
//        projectPath = "E:\\javaprojects\\playground-master";
        String localRepoPath = "";
        localRepoPath = MavenUtil.getLocalRepoPath();
        String selectGenPath = "E:\\javaprojects\\javalearn\\idealearn\\autotest\\src\\main\\java\\com\\pingan\\devtools\\generate\\testcase\\model";

        Set<String> classes = new HashSet<>();
        String root = ProjectFileUtil.getSourceRootForFile(selectGenPath, projectPath);
        ProjectFileUtil.scanFolder(new File(selectGenPath), classes, root);

        String[] classArray = classes.toArray(new String[classes.size()]);
        TestProjectInfo testProjectInfo = new TestProjectInfo(projectPath, localRepoPath, false, null
                , null, null, null, null, null,
                classArray, null, null, false,
                "", "", true,
                false, false, null);
        TestProject testProject = new TestProject(testProjectInfo);
        Set<String> modules = ProjectFileUtil.getModules(projectPath);
        testProject.generateTestSources(modules, false);
    }
}
