(ns understanding-re-frame.subscriptions
  (:require [reagent.core :as reagent]
            [clojure.string :as str]
            [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]
            [re-frame.db :as db]))

(def vehicles (zipmap (range) [{:name "V-Wing Fighter", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/CloneFlightSquadSevenVwings.jpg"} {:name "Coruscant Air Taxi", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_coruscantairtaxi_01_169_a36dcf1f.jpeg"} {:name "ATT Battle Tank", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_aatbattletank_01_169_9de46aea.jpeg"} {:name "Naboo N-1 Starfighter", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/N-1_Starfighter.png"} {:name "Vulture Droid", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Droid_Starfighters.png"} {:name "Soulless One", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_soullessone_01_169_08305d9b_0.jpeg"} {:name "Republic Cruiser", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_republiccruiser_01_169_fd769e33-1.jpeg"} {:name "Naboo Royal Starship", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Naboo_Royal_Starship.png"} {:name "Republic Attack Gunship", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_republicattackgunship_01_169_4ed5c0a7.jpeg"} {:name "Republic Attack Cruiser", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Venator_clonewars-1.jpg"}]))

(rf/reg-event-db
  :initialize-vehicles
  (fn [db]
    (assoc db :vehicles vehicles)))

(rf/reg-event-db
  :like
  (fn [db [_ id]]
    (update-in db [:user :likes] (fnil conj #{}) id)))

(rf/reg-event-db
  :unlike
  (fn [db [_ id]]
    (update-in db [:user :likes] disj id)))

(rf/reg-sub
  :vehicles
  (fn [db]
    (:vehicles db)))

(rf/reg-sub
  :likes
  (fn [db]
    (get-in db [:user :likes] #{})))

(defn vehicle-component [id vehicle]
  (let [likes @(rf/subscribe [:likes])]
   [:div {:style {:display :inline-block
                  :width 80}}
    [:img {:src (:image vehicle)
           :style {:max-width "100%"}}]
    [:a {:on-click (fn [e]
                     (.preventDefault e)
                     (if (contains? likes id)
                       (rf/dispatch [:unlike id])
                       (rf/dispatch [:like id])))
         :href "#"
         :style {:color (if (contains? likes id)
                          :red
                          :grey)
                 :text-decoration :none}}
     "♥"]]))

(defn subscriptions-panel []
  [:div
   [:h1 "Subscriptions"]
   [:div
    (doall
      (for [[id vehicle] @(rf/subscribe [:vehicles])]
        [:span {:key id}
         [vehicle-component id vehicle]]))
    [:div
     (pr-str @(rf/subscribe [:likes]))]]])

(rf/dispatch [:initialize-vehicles])

(comment

  vehicles

  (conj nil 1)


  )
