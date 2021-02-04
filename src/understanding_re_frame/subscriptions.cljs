(ns understanding-re-frame.subscriptions
  (:require [reagent.core :as reagent]
            [clojure.string :as str]
            [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]
            [re-frame.db :as db]))

(def vehicles (zipmap (range) [{:name "V-Wing Fighter", :image "/img/v-wing_8d8d05aa.jpeg"}
                               {:name "Coruscant Air Taxi", :image "/img/databank_coruscantairtaxi_01_169_a36dcf1f.jpeg"}
                               {:name "AAT Battle Tank", :image "/img/databank_aatbattletank_01_169_9de46aea.jpeg"}
                               {:name "Naboo N-1 Starfighter", :image "/img/databank_naboon1starfighter_01_169_26691adf.jpeg"}
                               {:name "Vulture Droid", :image "/img/databank_vulturedroid_01_169_6ef9fd50.jpeg"}
                               {:name "Soulless One", :image "/img/databank_soullessone_01_169_08305d9b.jpeg"}
                               {:name "Republic Cruiser", :image "/img/databank_republiccruiser_01_169_fd769e33.jpeg"}
                               {:name "Naboo Royal Starship", :image "/img/databank_nabooroyalstarship_01_169_e61f677e.jpeg"}
                               {:name "Republic Attack Gunship", :image "/img/databank_republicattackgunship_01_169_4ed5c0a7.jpeg"}
                               {:name "Republic Attack Cruiser", :image "/img/databank_republicattackcruiser_01_169_812f153d.jpeg"}
                               {:name "Solar Sailer", :image "/img/databank_geonosiansolarsailer_01_169_b3873578.jpeg"}
                               {:name "Gungan Bongo Submarine", :image "/img/databank_gunganbongosubmarine_01_169_fc9286be.jpeg"}
                               {:name "Flash Speeder", :image "/img/databank_flashspeeder_01_169_48978def.jpeg"}
                               {:name "Gian Speeder", :image "/img/databank_gianspeeder_01_169_70b62187.jpeg"}
                               {:name "Trade Federation Battleship", :image "/img/databank_tradefederationbattleship_01_169_fc5458ce.jpeg"}
                               {:name "Sith Speeder", :image "/img/databank_sithspeeder_01_169_cfa01a05.jpeg"}
                               {:name "Trade Federation Landing Ships", :image "/img/databank_tradefederationlandingship_01_169_00567a01.jpeg"}
                               {:name "ETA-2 Jedi Starfighter", :image "/img/ETA-2-starfighter-main-image_bedd3aaa.jpeg"}
                               {:name "Delta-7 Jedi Starfighter", :image "/img/delta-7-starfighter_fe9a59bc.jpeg"}
                               {:name "Sebulba’s Podracer", :image "/img/sebulbas-pod-racer_11e322c6.jpeg"}
                               {:name "Anakin’s Podracer", :image "/img/databank_anakinskywalkerspodracer_01_169_fe359d32.jpeg"}
                               {:name "General Grievous’ Tsmeu-6 Wheel Bike", :image "/img/ep3_ia_96565_46396938.jpeg"}
                               {:name "AT-TE Walker", :image "/img/databank_attewalker_01_169_4292c02c.jpeg"}
                               {:name "Tantive IV", :image "/img/tantive-iv-main_f1ea3fa5.jpeg"}
                               {:name "Cloud Car", :image "/img/cloud-car-main-image_8d2e4e89.jpeg"}
                               {:name "Sith Infiltrator", :image "/img/databank_sithinfiltrator_01_169_1bd0a638.jpeg"}
                               {:name "B-Wing", :image "/img/databank_bwingfighter_01_169_460cc528.jpeg"}
                               {:name "Escape Pod", :image "/img/databank_escapepod_01_169_2d71b62d.jpeg"}
                               {:name "Y-Wing Fighter", :image "/img/Y-Wing-Fighter_0e78c9ae.jpeg"}
                               {:name "GR-75 Medium Transport", :image "/img/gr-75-medium-transport_cd04862d.jpeg"}
                               {:name "Mon Calamari Star Cruiser", :image "/img/e6d_ia_2581_47f64de7.jpeg"}
                               {:name "AT-ST Walker", :image "/img/e6d_ia_5724_a150e6d4.jpeg"}
                               {:name "The Khetanna", :image "/img/the-khetanna_d1d5b294.jpeg"}
                               {:name "TIE Bomber", :image "/img/ep5_key_504_6c3982bb.jpeg"}
                               {:name "A-Wing Fighter", :image "/img/screen_shot_2015-05-26_at_5_16a39e17.png.jpeg"}
                               {:name "Snowspeeder", :image "/img/snowspeeder_ef2f9334.jpeg"}
                               {:name "Imperial Shuttle", :image "/img/veh_ia_1752_040381b2.jpeg"}
                               {:name "Super Star Destroyer", :image "/img/databank_superstardestroyer_01_169_d5757b90.jpeg"}
                               {:name "Sandcrawler", :image "/img/databank_sandcrawler_01_169_55acf6cb.jpeg"}
                               {:name "TIE Interceptor", :image "/img/tie-interceptor-2_b2250e79.jpeg"}
                               {:name "TIE Advanced x1", :image "/img/vaders-tie-fighter_8bcb92e1.jpeg"}
                               {:name "X-34 Landspeeder", :image "/img/E4D_IA_1136_6b8704fa.jpeg"}
                               {:name "Speeder Bike", :image "/img/speeder-bike-main_73f43e3a.jpeg"}
                               {:name "Death Star", :image "/img/Death-Star-I-copy_36ad2500.jpeg"}
                               {:name "AT-AT Walkers", :image "/img/AT-AT_89d0105f.jpeg"}
                               {:name "Imperial Star Destroyer", :image "/img/Star-Destroyer_ab6b94bb.jpeg"}
                               {:name "X-Wing Fighter", :image "/img/X-Wing-Fighter_47c7c342.jpeg"}
                               {:name "TIE Fighter", :image "/img/TIE-Fighter_25397c64.jpeg"}
                               {:name "Slave I", :image "/img/slave-i-main_1f3c9b0d.jpeg"}
                               {:name "Millennium Falcon", :image "/img/millennium-falcon-main-tlj-a_7cf89d3a.jpeg"}]))

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
  :vehicle-ids
  (fn []
    (rf/subscribe [:vehicles]))
  (fn [vehicles]
    (sort (keys vehicles))))

(rf/reg-sub
  :vehicle
  (fn []
    (rf/subscribe [:vehicles]))
  (fn [vehicles [_ id]]
    (get vehicles id)))

(rf/reg-sub
  :likes
  (fn [db]
    (get-in db [:user :likes] #{})))

(rf/reg-sub
  :liked?
  (fn []
    (rf/subscribe [:likes]))
  (fn [likes [_ id]]
    (contains? likes id)))

(rf/reg-sub
  :liked-vehicle
  (fn [[_ id]]
    [(rf/subscribe [:vehicle id])
     (rf/subscribe [:liked? id])])
  (fn [[vehicle liked?]]
    (assoc vehicle :liked? liked?)))

(defn vehicle-component [id]
  (let [vehicle @(rf/subscribe [:liked-vehicle id])
        liked?  (:liked? vehicle)]
   [:div {:style {:display :inline-block
                  :width 80}}
    [:img {:src (:image vehicle)
           :style {:max-width "100%"}}]
    [:a {:on-click (fn [e]
                     (.preventDefault e)
                     (if liked?
                       (rf/dispatch [:unlike id])
                       (rf/dispatch [:like id])))
         :href "#"
         :style {:color (if liked?
                          :red
                          :grey)
                 :text-decoration :none}}
     "♥"]]))

(defn subscriptions-panel []
  [:div
   [:h1 "Subscriptions"]
   [:div
    (doall
      (for [id @(rf/subscribe [:vehicle-ids])]
        [:span {:key id}
         [vehicle-component id]]))
    [:div
     (pr-str @(rf/subscribe [:likes]))]]])

(rf/dispatch [:initialize-vehicles])

(comment

  vehicles

  (conj nil 1)


  )
