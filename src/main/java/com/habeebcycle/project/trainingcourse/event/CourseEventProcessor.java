package com.habeebcycle.project.trainingcourse.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
@Slf4j
public class CourseEventProcessor implements ApplicationListener<CourseEvent>,
        Consumer<FluxSink<CourseEvent>> {

    private final Executor executor;
    private final BlockingQueue<CourseEvent> queue;

    public CourseEventProcessor(Executor executor) {
        this.executor = executor;
        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    public void onApplicationEvent(CourseEvent courseEvent) {
        this.queue.offer(courseEvent);
    }

    @Override
    public void accept(FluxSink<CourseEvent> courseEventFluxSink) {
        this.executor.execute(() -> {
            while (true) {
                try {
                    CourseEvent event = queue.take();
                    courseEventFluxSink.next(event);
                } catch (InterruptedException e) {
                    ReflectionUtils.rethrowRuntimeException(e);
                }
            }
        });
    }
}
