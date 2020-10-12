package com.javastack.sboot.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class JStackController {
    /**
     * 跟踪线程
     * 相对于jstack命令
     *
     * @return
     */
    @RequestMapping("track")
    @ResponseBody
    public String threadstack() {
        StringBuilder builder = new StringBuilder("");
        for (Map.Entry<Thread, StackTraceElement[]> stackTrace :
                Thread.getAllStackTraces().entrySet()) {
            Thread thread = (Thread) stackTrace.getKey();
            StackTraceElement[] stack = (StackTraceElement[]) stackTrace.getValue();
            if (thread.equals(Thread.currentThread())) {
                continue;
            }
            builder.append("\n线程： " + thread.getName() + "\n");
            System.out.println("\n线程： " + thread.getName() + "\n");
            for (StackTraceElement element : stack) {
                System.out.println("\t" + element + "\n");
                builder.append("\t" + element + "\n");
            }
            builder.append("==========================================\n");
        }
        return builder.toString();
    }

}
