/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package icecreamshop;

/**
 *
 * @author DELL
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

// interface for Ice Cream items
 interface Item {
    public String name();
    public double cost();
}

//abstect class for Icecream flavour

abstract class Flavour implements Item {
    private String flavourName;

    public Flavour(String flavourName) {
        this.flavourName = flavourName;
    }

    @Override
    public String name() {
        return flavourName + " scoop";
    }

    @Override
    public abstract double cost();
}

// abstract class for Topping 

abstract class Topping implements Item {
    private String toppingName;

    public Topping(String toppingName) {
        this.toppingName = toppingName;
    }

    @Override
    public String name() {
        return toppingName + " topping";
    }

    @Override
    public abstract double cost();
}

// abstract class for Syrup
abstract class Syrup implements Item {
    private String syrupName;

    public Syrup(String syrupName) {
        this.syrupName = syrupName;
    }

    @Override
    public String name() {
        return syrupName + " syrup";
    }

    @Override
    public abstract double cost();
}

class Vanilla extends Flavour {
    public Vanilla() {
        super("Vanilla");
    }

    @Override
    public double cost() {
        return 25.0;
    }
}

class Chocolate extends Flavour {
    public Chocolate() {
        super("Chocolate");
    }

    @Override
    public double cost() {
        return 50.0;
    }
}

class Sprinkle extends Topping {
    public Sprinkle() {
        super("Sprinkle");
    }

    @Override
    public double cost() {
        return 30.0;
    }
}

class Cashew extends Topping {
   
   public Cashew() {
      super("Cashew");
   }

   @Override
   public double cost() {
      return 35.0;
   }

}

class Choc extends Syrup {
    public Choc() {
        super("Chocolate");
    }

    @Override
    public double cost() {
        return 30.0;
    }
}

class Strawberry extends Syrup {
    public Strawberry() {
        super("Strawberry");
    }

    @Override
    public double cost() {
        return 35.0;
    }
}


// Ice cream class
class IceCream {
   private String name;
   private List<Item> items = new ArrayList<>();
   
   
   public void addItem(Item item){
      items.add(item);
   }

   public String getName(){
      return name;
   }

   public void setName(String name){
      this.name = name;
   }

   public double getCost(){
      double cost = 0.0;

      for (Item item : items) {
         cost += item.cost();
      }
      return cost;
   }

   public void showItems(){
      for (Item item : items) {
         System.out.print("Item : " + item.name());
         System.out.println(", Price : " + item.cost());
      }
   }
   
   public void printDetails(){
      System.out.println(getName()+" : "+ getCost());
      showItems();
      System.out.println("--------------------------");
   }
   
   public List<Item>getItems(){
       return items;
   }
}

//Bulider Pattern 

class IceCreamBuilder {
   private List<Item> itemList = new ArrayList<>();
   private String name;
  

   public IceCreamBuilder(String name) {
      this.name = name;
   }

   public IceCreamBuilder addFlavour(Flavour flavour) {
      itemList.add(flavour);
      return this;
   }

   public IceCreamBuilder addTopping(Topping topping) {
      itemList.add(topping);
      return this;
   }

   public IceCreamBuilder addSyrup(Syrup syrup) {
      itemList.add(syrup);
      return this;
   }
   
  // Bulid the custom  Ice cream
   public IceCream build() {
      IceCream customIceCream = new IceCream();
      customIceCream.setName(name);
      for (Item item : itemList) {
         customIceCream.addItem(item);
      }
      return customIceCream;
   }
}

// Favorite Icecream class

class FavoriteIceCream {
    private IceCream iceCream;

    public FavoriteIceCream(IceCream iceCream) {
        this.iceCream = iceCream;
    }

    public IceCream getIceCream() {
        return iceCream;
    }
}

// Customer class 

class Customer {
    private int id;
    private String name;
    private Map<String, FavoriteIceCream> favorites = new HashMap<>();
    private Order order;
    private LoyaltyProgram loyaltyProgram;
    private PaymentStrategy paymentStrategy;

    public Customer(String name, int id, LoyaltyProgram loyaltyProgram) {
        this.name = name;
        this.id = id;
        this.loyaltyProgram = loyaltyProgram;
    }

    public void saveFavorite(String favoriteName, IceCream iceCream, double amount) {
        favorites.put(favoriteName, new FavoriteIceCream(iceCream));
        loyaltyProgram.earnPoints(amount);
        if(paymentStrategy != null){
            paymentStrategy.processPayment(amount);
        }
    }

    public IceCream getFavorite(String favoriteName) {
        FavoriteIceCream favoriteIceCream = favorites.get(favoriteName);
        return (favoriteIceCream != null) ? favoriteIceCream.getIceCream() : null;
    }

    // Getters for name and id
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    public void setPaymentStrategy(PaymentStrategy paymentStrategy){
        this.paymentStrategy = paymentStrategy;
        
    }
    public PaymentStrategy getPaymentStrategy(){
        return paymentStrategy;
    }
    public void setDeliveryMethod(DeliveryMethod deliveryMethod){
        if(order != null){
            order.setDeliveryMethod(deliveryMethod);
        }
    }
 }
    

   //Observer Pattern

    interface Observer {
        void update(Order order); 
    }
 
    // Concrete observer
   class EmailClient implements Observer {
        private String email;
        
        public EmailClient(String email){
            this.email = email;
        }
        
        @Override
         public void update(Order order) {
           System.out.println("Sending email to " + email + ": Your order has been " + order.getStatus());
        }
    }
    
    class SmsClient implements Observer{
        private String number;
        
        public SmsClient(String number){
            this.number = number;
        }
        
        @Override
        public void update(Order order){
           System.out.println("Sending Sms to " + number + ": Your order has been " + order.getStatus());
        }
            
     }
     class AppNotifications implements Observer{
         
         @Override
         public void update(Order order){
            System.out.println("App notification: Your order has been " + order.getStatus());
         }
     }


//State Pattern
  interface OrderState{
      void handle(Order order);
  
  }
   // Placed state forr the order
   class PlacedState implements OrderState{
       
       @Override
       public void handle(Order order){
           order.setStatus("Placed");
       }
   }
   // Preparation state for the order
    class PreparationState implements OrderState{
      
       @Override
       public void handle(Order order){
           order.setStatus("In Preparation");
       }
   }
    // delivery state for the order
     class DeliveryState implements OrderState{
       
       @Override
       public void handle(Order order){
           order.setStatus("Delivered");
       }
   }

   // Context class
    class Order {
    private List<IceCream> items = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();
    private String status;
    private OrderState currentState;
    private SeasonalSpecial seasonalSpecial;
    private FlavourPromotion flavourPromotion;
    private DeliveryMethod deliveryMethod;
    
    
    public String getStatus(){
        return status;
    }
  
    
    public void setStatus(String status){
        this.status = status;
         notifyObservers();
        
    }
    
    public void setOrderstate(OrderState state){
        this.currentState = state;
    }
    
    public void handle(){
        currentState.handle(this);
    }
    
    private void notifyObservers(){
        for(Observer observer : observers){
            observer.update(this);
        }
    }
    
    public void addItem(IceCream item) {
        items.add(item);
    }
      
    
    public void attach(Observer observer){
        observers.add(observer);
    }
    
    public void removeItem(IceCream item) {
        items.remove(item);
    }
    
    public void setSeasonSpecial(SeasonalSpecial seasonalSpecial){
        this.seasonalSpecial = seasonalSpecial;
        
    }
    public void setFlavourPromotion(FlavourPromotion flavourPromotion){
        this.flavourPromotion = flavourPromotion;
    }
    
    public void setDeliveryMethod(DeliveryMethod deliveryMethod){
        this.deliveryMethod = deliveryMethod;
    }
    public DeliveryMethod getDeliveryMethod(){
        return deliveryMethod;
    }
    
     public double calculateTotalCostWithDelivery(){
         double totalCost = calculateTotalcost();
         if(deliveryMethod != null){
             totalCost += deliveryMethod.calculateDeliveryCost();
         }
         return totalCost;
     }
     
    
    public void displayDeliveryDetail(){
        if(deliveryMethod != null){
            deliveryMethod.displayDeliveryDetail();
        }
    }
    
    public double calculateTotalcost(){
        double totalCost = 0.0;
        
        for(IceCream iceCream : items){
            totalCost += iceCream.getCost();
        }
        if(seasonalSpecial != null){
            totalCost = seasonalSpecial.applySeasonDiscount(totalCost);
        }
        if(flavourPromotion != null){
            for(IceCream iceCream : items){
            if(flavourPromotion.isApplicable(iceCream)){
                totalCost = flavourPromotion.applyFlavorDiscount(totalCost);
                break;
            }
        }
            
        }
        return totalCost;
    }
    
    
    public void showItems() {
    System.out.println("Order Items");
    for (IceCream item : items) {
        System.out.println("Item: " + item.getName() + ", Price: Rs" + item.getCost());
    }
    System.out.println("Total Cost: Rs" + calculateTotalcost());
   }
    
    public List<IceCream> getItems(){
        return items;
    }
  }


// Chain of Responsibility Pattern
interface CustomizationHandler{
    void handleRequest(IceCream iceCream, String customization);
    void setNextHandler(CustomizationHandler nextHandler);
}

// handler for adding flavour customization
class FlavourHandler implements CustomizationHandler{
    
    private CustomizationHandler nextHandler;
    
    @Override
    public void setNextHandler(CustomizationHandler nextHandler){
        this.nextHandler = nextHandler;
    }
    
     @Override
    public void handleRequest(IceCream iceCream, String customization){
        if(customization.equalsIgnoreCase("Vanilla")){
            iceCream.addItem(new Vanilla());
        }else if(customization.equalsIgnoreCase("Chocolate")){
            iceCream.addItem(new Chocolate());
        } else if(nextHandler != null){
            nextHandler.handleRequest(iceCream, customization);
        }
    }
  
}
// handler for adding topping customization
class ToppingHandler implements CustomizationHandler{
    
    private CustomizationHandler nextHandler;
    
    @Override
    public void setNextHandler(CustomizationHandler nextHandler){
        this.nextHandler = nextHandler;
    }
    
    @Override
    public void handleRequest(IceCream iceCream, String customization){
        if(customization.equalsIgnoreCase("Sprinkle")){
            iceCream.addItem(new Sprinkle());
        } else if(customization.equalsIgnoreCase("Cashew")){
            iceCream.addItem(new Cashew());
        }else if(nextHandler != null){
            nextHandler.handleRequest(iceCream, customization);
        }
    
    }
  
}
// Handler for adding Syrup customization
class SyrupHandler implements CustomizationHandler{
    
    private CustomizationHandler nextHandler;
    
    @Override
    public void setNextHandler(CustomizationHandler nextHandler){
        this.nextHandler = nextHandler;
    }
    
     @Override
    public void handleRequest(IceCream iceCream, String customization){
        if(customization.equalsIgnoreCase("Choc")){
            iceCream.addItem(new Choc());
        }else if(customization.equalsIgnoreCase("Strawberry")){
            iceCream.addItem(new Strawberry());
        } else if(nextHandler != null){
            nextHandler.handleRequest(iceCream, customization);
        }
    }
  
}

// Stragery Pattern
interface PaymentStrategy{
    void processPayment(double amount);
}
//Concrete Strategies
class CreditCardPaymentStrategy implements PaymentStrategy{
    
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    
    public CreditCardPaymentStrategy(String cardNumber,String expirationDate, String cvv){
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }
    
    @Override
    public void processPayment(double amount) {
        connectToPaymentGateway();
        boolean paymentDetailsValid = verifyPaymentDetails();
        System.out.println("Processing credit card payment of Rs " + amount);
        if(paymentDetailsValid){
            boolean paymentProcessedSuccessfully = processPaymentInternally(amount);
            if(paymentProcessedSuccessfully){
            System.out.println("Credit card payment processed successfully");
            }else{
                System.out.println("Payment failed. Please try again");
            }
        }else{
            System.out.println("Payment failed. Invalif payment deatils");
        }
       
    }
    private void connectToPaymentGateway() {
        System.out.println("Connecting to payment gateway...");
    }

    private boolean verifyPaymentDetails() {
        System.out.println("Verifying payment details...");

        if(!isValidCardNumberFormat(cardNumber)|| !isValidCVVFormat(cvv)) {
            System.out.println("Invalid card number format.");
            return false;
        } else {
        }
        return true;
    }
    private boolean processPaymentInternally(double amount) {
       System.out.println("Processing payment of Rs "+ amount + "...");
        return true;
    }

    private boolean isValidCardNumberFormat(String cardNumber) {
        return cardNumber.matches("\\d{16}");
    }

    private boolean isValidCVVFormat(String cvv) {
        return cvv.matches("\\d{3}");
    }
}

class DigitalWalletPaymentStrategy implements PaymentStrategy{
    
    private String walletId;
    private String password;
    
    public DigitalWalletPaymentStrategy(String walletId, String password){
        this.walletId = walletId;
        this.password = password;
    }
    @Override
    public void processPayment(double amount){
        if(isValidWalletId(walletId) && isValidPassword(password)){
            System.out.println("Processing digital wallet payment of Rs" + amount);
        }else{
            System.out.println("Digital wallet payment failed");
        }
    }
    
    private boolean isValidWalletId(String walletId){
        return walletId != null && !walletId.isEmpty();
    }
    
    private boolean isValidPassword(String password){
        return password != null && !password.isEmpty();
    }
}

// LoyaltyProgram interface 
interface LoyaltyProgram{
    void earnPoints(double amount);
    double redeemPoints(double amount);
}

//Concrete LoyaltyProgram implemntation
class BasicLoyaltyProgram implements LoyaltyProgram{
    private int points = 0;
    
    @Override
    public void earnPoints(double amount){
        points += (int) (amount/10);
    }
    @Override
    public double redeemPoints(double amount){
        double discount = points * 0.1;
        points = 0;
        return discount;
    }
}

// SeasonalSpecial interface
interface SeasonalSpecial{
    boolean isAvaliable();
    double applySeasonDiscount(double amount);
  
}

// Concrete SeasonSpecial implementation
class ChristamsSeasonalSpecial implements SeasonalSpecial{
    
    @Override
    public boolean isAvaliable(){
        int currentMonth = java.time.LocalDate.now().getMonthValue();
        return currentMonth == 12;
    }
    
    @Override
    public double applySeasonDiscount(double amount){
        if (isAvaliable()) {
            double discount = amount * 0.15; // 15% discount during Christmas
            System.out.println("Applying Christmas Promotion. Discount: $" + discount);
            return amount - discount;
        }
        return amount;
        
    }
}

interface FlavourPromotion{
    boolean isApplicable(IceCream iceCream);
    double applyFlavorDiscount(double amount);
}


class ChocolatePromotion implements FlavourPromotion{
    @Override
    public boolean isApplicable(IceCream iceCream) {
        // Check if the promotion is applicable to the given ice cream flavor
        return iceCream.getItems().stream().anyMatch(item -> item instanceof Chocolate);
    }

    @Override
    public double applyFlavorDiscount(double amount) {
        double discount = amount * 0.20; // 20% discount for Chocolate flavor
        System.out.println("Applying Chocolate Flavor Promotion. Discount: $" + discount);
        return amount - discount;
    }
}

// Command pattern
interface Command {
    void execute();
    
}
// Concrte Commands
class PlaceOrderCommmand implements Command{
    private Order order;
    
    public PlaceOrderCommmand (Order order){
        this.order = order;
       
    }
    @Override
    public void execute(){
        order.handle();
        System.out.println("Order Placed Successfully");
    }
}

class ProvideFeedbackCommand implements Command {
    private String feedback;
    private Customer customer;
    
    public ProvideFeedbackCommand(Customer customer, String feedback){
        this.customer = customer;
        this.feedback = feedback;
    }
    @Override
    public void execute(){
        System.out.println("Feedback provided : " + feedback);
    } 
}
//Command Invoker class 
class CommandInvoker{
    private List<Command> commandQueue = new ArrayList<>();
    
    public void addCommand(Command command){
        commandQueue.add(command);
    }
    
    public void executeCommands(){
        for(Command command : commandQueue){
            command.execute();
        }
        commandQueue.clear();
    }
    
}


// Decorater Pattern
abstract class OrderDecorator extends Order{
    protected Order decoratedOrder;
    
    public OrderDecorator(Order decoratedOrder){
        this.decoratedOrder = decoratedOrder;
    }
    @Override
    public void showItems(){
        decoratedOrder.showItems();
    }
    @Override
    public double calculateTotalcost(){
        return decoratedOrder.calculateTotalcost();
    }
}

//Concrete decorator for adding gift wrapping
class GiftWrappingDecorator extends OrderDecorator{
    
    public GiftWrappingDecorator(Order decoratedOrder){
        super(decoratedOrder);
    }
    @Override
    public void showItems(){
        super.showItems();
        System.out.println("Gift Wrapping Included");
    }
    @Override
    public double calculateTotalcost(){
        return super.calculateTotalcost() + 50;
    }
}

// Concrete decorator for adding special packaging 
class SpecialPackingDecorator extends OrderDecorator{
    
    public SpecialPackingDecorator(Order decoratedOrder){
        super(decoratedOrder);
    }
    
    @Override
    public void showItems(){
        super.showItems();
        System.out.println("Special Packaging Included");
    }
    @Override
    public double calculateTotalcost(){
        return super.calculateTotalcost() + 100;
    }
}


//Factory Pattern
interface DeliveryMethod{
    String getOption();
    void displayDeliveryDetail();
    double calculateDeliveryCost();
    
}

class Pickup implements DeliveryMethod{
    
    @Override
    public String getOption(){
        return "Pickup";
    }
    
    @Override
    public void displayDeliveryDetail(){
        System.out.println("Pick up your order at our store");
    }
    
    @Override
    public double calculateDeliveryCost(){
        return 0.0;
    }
}

class Delivery implements DeliveryMethod{
    private String address;
    
    public Delivery(String address){
        this.address = address;
    }
    
    @Override
    public String getOption(){
        return "Delivery";
    }
    
    @Override
    public void displayDeliveryDetail(){
        System.out.println("Deliver to: " + address);
    }
    
    @Override
    public double calculateDeliveryCost(){
        return 50.0;
    }
    
}





 public class Icecreamshop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      
        // BuliderPattern
        IceCreamBuilder iceCreamBuilder = new IceCreamBuilder("Custom Ice Cream 1");
        iceCreamBuilder.addFlavour(new Vanilla())
                 .addTopping(new Sprinkle())
                 .addSyrup(new Choc());
        
        IceCream customIceCream = iceCreamBuilder.build();
        
        
        //Chain of Responsibility pattern
        CustomizationHandler flavourHandler = new FlavourHandler();
        CustomizationHandler toppingHandler = new ToppingHandler();
        CustomizationHandler syrupHandler = new SyrupHandler();
        
        //Set up the chain
        flavourHandler.setNextHandler(toppingHandler);
        toppingHandler.setNextHandler(syrupHandler);
        
       
        // Customizations
        String[] customizations = {"Chocolate","Sprinkle","Chashew"};
        
        
       
        for(String customization : customizations){
            flavourHandler.handleRequest(customIceCream, customization);
        }
        
        System.out.println("------------------------");
        
        
        //Observer Pattern
        
        Order order = new Order();
        order.addItem(customIceCream);
        
        Observer emailClient = new EmailClient("John@gmail.com");
        Observer smsClient = new SmsClient("0771082078");
        Observer appNotifications = new AppNotifications();
        
        order.attach(emailClient);
        order.attach(smsClient);
        order.attach(appNotifications);
        
       
        //State pattern
      
        OrderState orderState = new PlacedState();
        
        order.setOrderstate(orderState);
        order.handle();
        
        orderState  = new PreparationState();
        order.setOrderstate(orderState);
        order.handle();
        
        orderState  = new DeliveryState();
        order.setOrderstate(orderState);
        order.handle();
        
        
        //Strategy Pattern
        
         //Set payment strategy for the customer
        PaymentStrategy creditCardPayment = new CreditCardPaymentStrategy("1433567858123446","08/25","744");
        
        Customer customer = new Customer("John", 1, new BasicLoyaltyProgram());
        customer.setPaymentStrategy(creditCardPayment);
        
        //save Favourite icecream for customer
        customer.saveFavorite("Favorite 1", customIceCream, customIceCream.getCost());

        // Retrieving a favorite ice cream and displaying customer details
        IceCream favoriteIceCream = customer.getFavorite("Favorite 1");
        if (favoriteIceCream != null) {
            favoriteIceCream.printDetails();
        }
       
        //Process payment 
        double amountToPay = favoriteIceCream.getCost();
        customer.getPaymentStrategy().processPayment(amountToPay);
        
        System.out.println("----------------------");
        System.out.println("Customer Details - ID: " + customer.getId() + ", Name: " + customer.getName());
        
        
        // Using factory patternfor delivery 
        order.setDeliveryMethod(new Delivery("101/34 Galle Road, Colombo"));
        order.displayDeliveryDetail();
        double totalCostwithDelivery = order.calculateTotalCostWithDelivery();
        System.out.println("Total Cost with Delivery: Rs " + totalCostwithDelivery);
      
        
        
        //Decorate Pattern 
        
        // Create a basic order
        Order giftWrappedOrder = new GiftWrappingDecorator(order);
        
        Order specialPackingOrder = new SpecialPackingDecorator(giftWrappedOrder);
        
        
        double totalCostwithDecoration = specialPackingOrder.calculateTotalcost();
        System.out.println("Total Cost with Decorations: Rs " + totalCostwithDecoration);
        
        
        //Command Pattern
        
        CommandInvoker commandInvoker = new CommandInvoker();
        Command placeOrderCommand = new PlaceOrderCommmand(order);
        Command provideFeedbackCommand = new ProvideFeedbackCommand (customer," Had a Great service");
         
        //Adding commands to the command invoker
        commandInvoker.addCommand(placeOrderCommand);
        commandInvoker.addCommand(provideFeedbackCommand);
        
        //Executing commands
        commandInvoker.executeCommands();
        
        
    }
    
 }

