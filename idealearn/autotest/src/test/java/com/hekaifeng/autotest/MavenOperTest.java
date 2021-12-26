package com.hekaifeng.autotest;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.apache.maven.project.MavenProject;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MavenOperTest {

    @Test
    public void testMavenOper() throws IOException {
        Model model = getModel("E:\\javaprojects\\javalearn\\idealearn\\autotest\\pom.xml");

        Model model1 = getModel("E:\\javaprojects\\javalearn\\idealearn\\test11\\pom.xml");
        MavenProject project = new MavenProject(model);
        List<Dependency> dependencies = model.getDependencies();
        model1.getDependencies().addAll(dependencies);


        MavenXpp3Writer mavenXpp3Writer = new MavenXpp3Writer();
        mavenXpp3Writer.write(new FileWriter("E:\\javaprojects\\javalearn\\idealearn\\test11\\pom.xml"), model1);
    }

    private Model getModel(String pomFile) {
        MavenXpp3Reader mavenreader = new MavenXpp3Reader();
        try (FileReader reader = new FileReader(pomFile);
        ) {
            Model model = mavenreader.read(reader);
            model.setPomFile(new File(pomFile));
            return model;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }
}
