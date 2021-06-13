import java.util.*;

public class StockMarket {
    private int index;
    private final Map<String, Integer> prices;
    private final List<Broker> brokers;
    private boolean indexFlag;

    public StockMarket() {
        index = 1000;
        prices = new HashMap<>();
        brokers = new ArrayList<>();
        indexFlag = false;
    }

    public static class Stock {
        private final String name;
        private int amount;

        public Stock(String name, int amount) {
            this.name = name;
            this.amount = amount;
        }

        public int decrAmount(int decr) {
            amount = amount - decr;
            if (amount < 0) {
                amount = 0;
                return 0;
            }

            return  decr;
        }

        public int incrAmount(int incr) {
            amount = amount + incr;
            return incr;
        }
    }

    public static class Broker {
        private final String name;
        private final List<Stock> availableStocks;

        public Broker(String name) {
            this.name = name;
            availableStocks = new ArrayList<>();
        }

        public void addStock(Stock stock) {
            availableStocks.add(stock);
        }

        public int buy(String name) {
            for (Stock stock : availableStocks) {
                if (stock.name.equals(name)) {
                    Random rand = new Random();
                    int randomInt = rand.nextInt(50);
                    return stock.incrAmount(randomInt);
                }
            }
            return 0;
        }

        public int sell(String name) {
            for (Stock stock : availableStocks) {
                if (stock.name.equals(name)) {
                    Random rand = new Random();
                    int randomInt = rand.nextInt(50);
                    return stock.decrAmount(randomInt);
                }
            }
            return 0;
        }
    }

    public void initializeStockMarket() {
        prices.put("Adidas", 1000);
        prices.put("Nike", 1200);
        prices.put("Tesla", 900);
        prices.put("Apple", 3000);
        prices.put("SpaceX", 1500);

        Stock stock1 = new Stock("Adidas", 500);
        Stock stock2 = new Stock("Adidas", 600);
        Stock stock3 = new Stock("Nike", 400);
        Stock stock4 = new Stock("Nike", 500);
        Stock stock5 = new Stock("Tesla", 300);
        Stock stock6 = new Stock("Tesla", 200);
        Stock stock7 = new Stock("Apple", 100);
        Stock stock8 = new Stock("Apple", 200);

        Broker broker1 = new Broker("Boris");
        broker1.addStock(stock1);
        broker1.addStock(stock5);
        Broker broker2 = new Broker("Oleg");
        broker2.addStock(stock2);
        broker2.addStock(stock8);
        Broker broker3 = new Broker("Ihor");
        broker3.addStock(stock3);
        broker3.addStock(stock7);
        Broker broker4 = new Broker("Sasha");
        broker4.addStock(stock4);
        broker4.addStock(stock6);

        brokers.add(broker1);
        brokers.add(broker2);
        brokers.add(broker3);
        brokers.add(broker4);
    }

    private synchronized int decreasePrice(String name, int amountSold) {
        if (prices.containsKey(name)) {
            Random rand = new Random();
            int randomInt = rand.nextInt(amountSold);
            int diff = Math.max(prices.get(name) - randomInt, 0);
            prices.put(name, diff);
            index = index - randomInt;
            if (index < 0) {
                index = 0;
            }
            return diff;
        }
        return 0;
    }

    private synchronized int increasePrice(String name, int amountBought) {
        if (prices.containsKey(name)) {
            Random rand = new Random();
            int randomInt = rand.nextInt(amountBought);
            prices.put(name, prices.get(name) + randomInt);
            index = index - randomInt;
            if (index < 0) {
                index = 0;
            }
            return prices.get(name) + randomInt;
        }
        return 0;
    }

    public void startMarket() {
        Thread broker1Thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(indexFlag) {
                    return;
                }
                Broker broker = brokers.get(0);
                Random rand = new Random();
                int randomInt = rand.nextInt(100);


                if (randomInt >= 50) {
                    String stockName = "Adidas";
                    int diff = broker.sell(stockName);
                    int priceDiff = decreasePrice(stockName, diff);

                    System.out.println("Broker " + broker.name + " has sold " + stockName + " stocks of amount: " + diff);
                    System.out.println("The price decreased:" + priceDiff);
                    System.out.println("The index is now:" + index);
                } else {
                    String stockName = "Tesla";
                    int diff = broker.buy(stockName);
                    int priceDiff = increasePrice(stockName, diff);

                    System.out.println("The price increased:" + priceDiff);
                    System.out.println("Broker " + broker.name + " has bought " + stockName + " stocks of amount: " + diff);
                    System.out.println("The index is now:" + index);
                }
            }
        });

        Thread broker2Thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(indexFlag) {
                    return;
                }
                Broker broker = brokers.get(1);
                Random rand = new Random();
                int randomInt = rand.nextInt(100);


                if (randomInt >= 50) {
                    String stockName = "Adidas";
                    int diff = broker.sell(stockName);
                    int priceDiff = increasePrice(stockName, diff);

                    System.out.println("The price decreased:" + priceDiff);
                    System.out.println("Broker " + broker.name + " has sold " + stockName + " stocks of amount: " + diff);
                    System.out.println("The index is now:" + index);
                } else {
                    String stockName = "Apple";
                    int diff = broker.buy(stockName);
                    int priceDiff = increasePrice(stockName, diff);

                    System.out.println("The price increased:" + priceDiff);
                    System.out.println("Broker " + broker.name + " has bought " + stockName + " stocks of amount: " + diff);
                    System.out.println("The index is now:" + index);
                }
            }
        });

        Thread broker3Thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(indexFlag) {
                    return;
                }
                Broker broker = brokers.get(2);
                Random rand = new Random();
                int randomInt = rand.nextInt(100);


                if (randomInt >= 50) {
                    String stockName = "Apple";
                    int diff = broker.sell(stockName);
                    int priceDiff = increasePrice(stockName, diff);

                    System.out.println("The price decreased:" + priceDiff);
                    System.out.println("Broker " + broker.name + " has sold " + stockName + " stocks of amount: " + diff);
                    System.out.println("The index is now:" + index);
                } else {
                    String stockName = "Nike";
                    int diff = broker.buy(stockName);
                    int priceDiff = increasePrice(stockName, diff);

                    System.out.println("The price increased:" + priceDiff);
                    System.out.println("Broker " + broker.name + " has bought " + stockName + " stocks of amount: " + diff);
                    System.out.println("The index is now:" + index);
                }
            }
        });

        Thread indexWatcher = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (index <= 500) {
                    System.out.println("INDEX IS SMALL");
                    indexFlag = true;
                    return;
                }
            }
        });

        broker1Thread.start();
        broker2Thread.start();
        broker3Thread.start();
        indexWatcher.start();

    }
}
