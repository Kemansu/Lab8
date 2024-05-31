package org.sc;

import org.sc.manager.CommandManager;

import java.util.concurrent.RecursiveTask;

public class ProcessTask extends RecursiveTask<String> {
    private Request request;
    public ProcessTask(Request request){
        this.request = request;
    }
    @Override
    protected String compute() {
        try {
            return CommandManager.startExecuting(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
