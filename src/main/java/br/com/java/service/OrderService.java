package br.com.java.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.java.dto.cart.CartDto;
import br.com.java.dto.cart.CartItemDto;
import br.com.java.dto.checkout.CheckoutItemDto;
import br.com.java.dto.order.PlaceOrderDto;
import br.com.java.model.Order;
import br.com.java.model.OrderItem;
import br.com.java.model.User;
import br.com.java.repository.OrderRepository;

@Service
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    OrderItemsService orderItemsService;

    @Value("${BASE_URL}")
    private String baseURL;

    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;

    public Order saveOrder(PlaceOrderDto orderDto, User user, String sessionID) {
        Order order = getOrderFromDto(orderDto, user, sessionID);

        return orderRepository.save(order);
    }

    private Order getOrderFromDto(PlaceOrderDto orderDto, User user, String sessionID) {
        Order order = new Order(orderDto, user, sessionID);

        return order;
    }

    public List<Order> listOrders(User user){
        List<Order> orderList = orderRepository.finAllByUserOrderByCreateDateDesc(user);

        return orderList;
    }

    public Order getOrder(int orderId) throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            
            return order.get();
        }

        throw new OrderNotFoundException("Perdido não encontrado");
    }

    public void placeOrder(User user, String sessionId){
        CartDto cartDto = cartService.listCartItems(user);

        PlaceOrderDto placeOrderDto = new PlaceOrderDto();
        placeOrderDto.setUser(user);
        placeOrderDto.setTotalPrice(cartDto.getTotalCost());

        Order newOrder = saveOrder(placeOrderDto, user, sessionId);
        List<CartItemDto> cartItemDtoList = cartDto.getCartItems();

        for(CartItemDto cartItemDto : cartItemDtoList) {
            OrderItem orderItem = new OrderItem(
                newOrder,
                cartItemDto.getProduct(),
                cartItemDto.getQuantity(),
                cartItemDto.getProduct().getPrice());

                orderItemsService.addOrderedProducts(orderItem);
        }
        cartService.deleteUserCartItems(user);
    }

    SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.Price.builder()
                 .setCurrency("usd")
                 .setUnitAmount( ((long) checkoutItemDto.getPrice()) * 100)
                 .setProductData(
                     SessionCreateParams.LineItem.PriceData.ProductData.builder()
                     .setName(checkoutItemDto.getProductName())
                     .build());
    }

    SessionCreateParams.LineItem.createSessionLineItem(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.builder()
                  .setPriceData(createPriceData(checkoutItemDto))
                  .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                  .build();
    }

    public Session createSession(List<CheckoutItemDto> checkoutItemDtoList) {

        String successURL = baseURL + "payment/success";
        String failedURL = baseURL + "payment/failed";

        Stripe.apiKey = apiKey;

        List<SessionCreateParams.LineItem> sessionItemsList = new ArrayList<SessionCreateParams.LineItem>();

        for
    }
}
