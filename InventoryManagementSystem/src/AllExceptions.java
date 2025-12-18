public class AllExceptions{
    //used in Item ,Mission classes
static class InvalidNumberException extends Exception{
            InvalidNumberException(String alert){
                super(alert);
            }
    }
    //used in Mission class
static class InvalidDateException extends Exception{
        InvalidDateException(String alert){
            super(alert);
        }
    }
    //used in Inventory class
    static class LowStockException extends Exception {
        LowStockException(String alert) {
            super(alert);
        }
    }
}
