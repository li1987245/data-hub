package cn.credit;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    static ScriptEngineManager manager = new ScriptEngineManager();
    static ScriptEngine engine = manager.getEngineByName("javascript");

    //	static NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
    public static void main(String[] args) throws Exception {
//		CompiledScript compiledScript = engine.compile("Math.pow(obj,2)");
        int time = 10000;
        ExecutorService executor = Executors.newFixedThreadPool(100);
        final CountDownLatch latch = new CountDownLatch(time);
        final AtomicInteger right = new AtomicInteger(0);
        final AtomicInteger error = new AtomicInteger(0);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            final int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
//					ScriptEngine engine = manager.getEngineByName("javascript");
//				    Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
                    Bindings bindings = engine.createBindings();
                    bindings.put("obj", finalI);
                    try {
                        if (Double.parseDouble(engine.eval("Math.pow(obj,2)", bindings).toString()) == Math.pow(finalI, 2))
                            right.incrementAndGet();
                        else
                            error.incrementAndGet();
//						if(Double.parseDouble(compiledScript.eval(bindings).toString())==Math.pow(finalI,2))
//							right.incrementAndGet();
//						else
//							error.incrementAndGet();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    latch.countDown();
                }
            });
        }
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
        System.out.println("right:" + right.get() + "\t error:" + error.get());
        executor.shutdown();
    }

}
