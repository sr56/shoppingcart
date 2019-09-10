# ShoppingCart
### ShoppingCart implementation 

Springboot project.

Demonstrates a Shopping cart application, that 
- Creates a Store with 20 Categories of items with each category having 10 items each.
- Each item has a price ( $1-$20 ), shippingcost ($2- $10) and rating ( 1-5)
- Items in the store will have it's price/shippingcost/rating assigned randomly.
- Bigger Rating means better.

The shopping Cart needs to be filled with as many number of items as possible and with the maximum rating possible.
The total cost of items including price and shipping cost should be <= **$50.**
Only one item can be taken from a category in the shoppingcart.


#### Implementation

**Store** is designed as a base that holds items
***Category*** is a property of the item that is also added to the item's name for sorting and identification.
**ShoppingCart** is an extension of Store with extra functionaliites such as aggregation of price, and ratings of the contents.

**StoreService** manages the initialization of Store and ensuring the items are there as per rule.
**ShopperService** manages the ShoppingCart and manages the rules of the shopping cart.

The logic behind shopping is decoupled into a shopping strategy object, which prioritises the items in the Store 
The **ShopperService** just takes the next item from this list, if it satisfies the rule of shopping.

**ShopperService** also makes sure the total amount spent doesn't exceed $50.

**MaxItemCountMaxRatingStrategy** class is used as shopping strategy, it sorts the available items in the store in 
 -  Ascending order of Price   ( So that the cheaper items are selected first to maximise number of items )
 -   Descending order of Rating ( So that given a choice, the item with better rating will be chosen first )
   
Finally the program prints the Shopping Cart and it's Content summary in the **Console / Log**  as  an **INFO**.


