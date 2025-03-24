public class Test {

    private static final ThreadLocal<Long> userThreadLocal = new ThreadLocal<>();
    public static void main(String[] args) {
        userThreadLocal.set(1L);
    }
}
