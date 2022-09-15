package com.slackjk.servermonitor.FDcode;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@Route(value = "test")
@PermitAll

public class TestView extends Composite<Div> {

    private TextField tfDataFromServer;

    public TestView(){

        tfDataFromServer = new TextField("empty");
        MyCustomThread myThread = new MyCustomThread(UI.getCurrent(), this);

        Button someButton1 = new Button("start myThread");
        someButton1.addClickListener(e->{
            myThread.start();
        });

        getContent().add(tfDataFromServer,someButton1);

    }

    //inner class
    private static class MyCustomThread extends Thread {
        private final UI ui;
        private final TestView testView;

        private int count = 0;

        public MyCustomThread(UI ui, TestView testView) {
            this.ui = ui;
            this.testView = testView;
        }

        @Override
        public void run() {
            try {
                // Update the data for a while
                count = 0;
                while (count < 10) {
                    Thread.sleep(500);
                    System.out.println(count);
                    count++;
                }

                // Inform that we are done
                ui.access(() -> {
                    testView.tfDataFromServer.setValue("SomeData");
                });

                // Update the data for another while
                while (count < 20) {
                    Thread.sleep(500);
                    System.out.println(count);
                    count++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
