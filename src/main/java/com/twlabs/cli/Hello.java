package com.twlabs.cli;

import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "hello", description = "Hello World Picocli.")
class Hello implements Callable<Integer> {

    @Option(names = {"-n", "--name"}, description = "Your name..")
    private String name = "unknown";

    @Override
    public Integer call() throws Exception {
        System.out.println("Hello " + name + "!");
        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new Hello()).execute(args);
        System.exit(exitCode);
    }
}
