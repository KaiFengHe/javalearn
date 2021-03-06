package com.hekaifeng.autotest.utgen;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ToolWindowType;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Entry point for the IntelliJ plugin for when projects are opened/closed
 * <p>
 * <p/>
 * Created by arcuri on 9/9/14.
 */
public class ProjectRegistration implements ProjectComponent { //implements ApplicationComponent {

    private final Project project;

    private ConsoleViewImpl console;

    public ProjectRegistration(Project project) {
        this.project = project;
    }


    // Returns the component name (any unique string value).
    @Override
    @NotNull
    public String getComponentName() {
        return "UT Plugin";
    }


    // If you register the ProjectRegistration class in the <application-components> section of
    // the plugin.xml file, this method is called on IDEA start-up.
    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {
        Parameters.getInstance().save(project);
    }

    @Override
    public void projectOpened() {
        Parameters.getInstance().load(project);
        //ActionManager am = ActionManager.getInstance();

        //create the tool window, which will appear in the bottom when an EvoSuite run is started
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        ToolWindow toolWindow = toolWindowManager.registerToolWindow("UT Plugin", false, ToolWindowAnchor.BOTTOM);
        toolWindow.setTitle("UT Plugin Console Output");
        toolWindow.setIcon(StartAction.loadIcon());
        toolWindow.setType(ToolWindowType.DOCKED, null);

        //create a console panel
        console = (ConsoleViewImpl) TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        JComponent consolePanel = console.getComponent();

        IntelliJNotifier notifier = IntelliJNotifier.registerNotifier(project, "UT Plugin", console);

        //create left-toolbar with stop button
//        DefaultActionGroup buttonGroup = new DefaultActionGroup();
//        buttonGroup.add(new StopAction(notifier));
//        ActionToolbar viewToolbar = am.createActionToolbar("UT.ConsoleToolbar", buttonGroup, false);
//        JComponent toolBarPanel = viewToolbar.getComponent();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        //panel.add(toolBarPanel, BorderLayout.WEST);
        panel.add(consolePanel, BorderLayout.CENTER);

        Content content = contentFactory.createContent(panel, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    @Override
    public void projectClosed() {
        Parameters.getInstance().save(project);
        if (console != null) {
            console.dispose();
        }
    }

}