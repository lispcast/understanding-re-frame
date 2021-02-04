(ns understanding-re-frame.database-structure
  (:require [re-frame.core :as rf]
            [reagent.core :as reagent]))

(def db {:user {:id 123
                :email "eric@lispcast.com"}
         :shopping-cart {:items []}
         :checkout {:coupon-code "434fdsfs"
                    :shipping-preference {}
                    :address {}
                    :billing-information {}}
         :products {1256 {:id 1256
                          :name "Car"
                          :description "..."
                          :price 200}
                    434 {:id 434
                         :name "Truck"}}
         })

(comment
  (assoc-in db [:a :b] 1)

  (assoc-in db [:products 564] {:id 564 :name "Truck"})

  (update-in db [:products 1256 :name] str " for sale")

  (rf/reg-event-db
    :increment-price
    (fn [db [_ product-id]]
      (update-in db [:products product-id :price] inc)))
  
  (let [product-id 1256]
    (update-in db [:products product-id :price] inc))

  (let [product-id 434]
   (update-in db [:products product-id :price] (fnil inc 0)))


  (rf/reg-sub
    :product-name
    (fn [db [_ product-id]]
      (get-in db [:products product-id :name])))
  
  
  )

