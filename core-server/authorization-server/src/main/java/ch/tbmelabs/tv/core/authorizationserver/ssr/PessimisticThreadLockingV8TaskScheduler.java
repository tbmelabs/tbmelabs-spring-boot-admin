package ch.tbmelabs.tv.core.authorizationserver.ssr;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.eclipsesource.v8.NodeJS;

public class PessimisticThreadLockingV8TaskScheduler {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(PessimisticThreadLockingV8TaskScheduler.class);

  private static volatile boolean handleMessages = false;

  private volatile ThreadPoolTaskExecutor taskScheduler;

  private volatile NodeJS nodeJs;

  public PessimisticThreadLockingV8TaskScheduler(NodeJS nodeJs) {
    this(nodeJs, false);
  }

  public PessimisticThreadLockingV8TaskScheduler(NodeJS nodeJs, boolean autostart) {
    this.nodeJs = nodeJs;

    taskScheduler = new ThreadPoolTaskExecutor();
    taskScheduler.setThreadNamePrefix("PessimisticThreadLockingV8TaskScheduler-");
    taskScheduler.setMaxPoolSize(2);
    taskScheduler.afterPropertiesSet();

    if (autostart) {
      start();
    }
  }

  public void start() {
    if (PessimisticThreadLockingV8TaskScheduler.handleMessages) {
      return;
    }

    LOGGER.info("Starting {}", AngularUniversalRenderEngine.class);

    PessimisticThreadLockingV8TaskScheduler.handleMessages = true;

    releaseThreadLock();

    taskScheduler.execute(() -> {
      while (PessimisticThreadLockingV8TaskScheduler.handleMessages) {
        try {
          LOGGER.info("I am handling {} incoming request(s)!", taskScheduler.getActiveCount());
          schedule(newMessageHandlerTask()).get();
        } catch (InterruptedException | ExecutionException e) {
          throw new IllegalArgumentException(e);
        }
      }
    });
  }

  private Runnable newMessageHandlerTask() {
    return () -> {
      nodeJs.handleMessage();
    };
  }

  public FutureTask<?> schedule(Runnable task) {
    return (FutureTask<?>) taskScheduler.submit(() -> {
      acquireThreadLock();

      task.run();

      releaseThreadLock();
    });
  }

  private void acquireThreadLock() {
    nodeJs.getRuntime().getLocker().acquire();
  }

  private void releaseThreadLock() {
    nodeJs.getRuntime().getLocker().release();
  }

  public void stop() {
    LOGGER.info("Stopping {}", AngularUniversalRenderEngine.class);

    PessimisticThreadLockingV8TaskScheduler.handleMessages = false;
  }
}
