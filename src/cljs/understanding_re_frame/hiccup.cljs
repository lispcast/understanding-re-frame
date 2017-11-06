(ns understanding-re-frame.hiccup
  (:require [reagent.core :as reagent]
            [clojure.string :as str]))

(def vehicles (sort-by :name [{:name "V-Wing Fighter", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/CloneFlightSquadSevenVwings.jpg"} {:name "Coruscant Air Taxi", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_coruscantairtaxi_01_169_a36dcf1f.jpeg"} {:name "ATT Battle Tank", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_aatbattletank_01_169_9de46aea.jpeg"} {:name "Naboo N-1 Starfighter", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/N-1_Starfighter.png"} {:name "Vulture Droid", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Droid_Starfighters.png"} {:name "Soulless One", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_soullessone_01_169_08305d9b_0.jpeg"} {:name "Republic Cruiser", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_republiccruiser_01_169_fd769e33-1.jpeg"} {:name "Naboo Royal Starship", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Naboo_Royal_Starship.png"} {:name "Republic Attack Gunship", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_republicattackgunship_01_169_4ed5c0a7.jpeg"} {:name "Republic Attack Cruiser", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Venator_clonewars-1.jpg"} {:name "Solar Sailer", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Solar-Sailer.jpg"} {:name "Gungan Bongo Submarine", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Bongo.jpg"} {:name "Flash Speeder", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Flash_Speeder.jpg"} {:name "Gian Speeder", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Gian.jpg"} {:name "Trade Federation Battleship", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_tradefederationbattleship_01_169_fc5458ce.jpeg"} {:name "Sith Speeder", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_sithspeeder_01_169_cfa01a05.jpeg"} {:name "Trade Federation Landing Ships", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Trade_Federation_Invasion.png"} {:name "ETA-2 Jedi Starfighter", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/AnakinsEta2.jpg"} {:name "Delta-7 Jedi Starfighter", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Delta7-SWE.jpg"} {:name "Sebulba’s Podracer", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Podrace.png"} {:name "Anakin’s Podracer", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/PodracerAnakin.jpg"} {:name "General Grievous’ Tsmeu-6 Wheel Bike", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/ep3_ia_96565_46396938.jpeg"} {:name "AT-TE Walker", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/ATTE-ST.jpg"} {:name "Tantive IV", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_alderaancruiser_01_169_c60ce268.jpeg"} {:name "Cloud Car", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/cloud-car-main-image_8d2e4e89.jpeg"} {:name "Sith Infiltrator", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Sith_Infiltrator_hatch.png"} {:name "B-Wing", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_bwingfighter_01_169_460cc528.jpeg"} {:name "Escape Pod", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/EscapePod_bg.jpg"} {:name "Y-Wing Fighter", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Y-Wing-Fighter_0e78c9ae.jpeg"} {:name "GR-75 Medium Transport", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/GR-75_Medium_Transport.jpg"} {:name "Mon Calamari Star Cruiser", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/e6d_ia_2581_47f64de7.jpeg"} {:name "AT-ST Walker", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/e6d_ia_5724_a150e6d4.jpeg"} {:name "The Khetanna", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/the-khetanna_d1d5b294.jpeg"} {:name "TIE Bomber", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/ep5_key_504_6c3982bb.jpeg"} {:name "A-Wing Fighter", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/screen_shot_2015-05-26_at_5_16a39e17.png"} {:name "Snowspeeder", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/snowspeeder_ef2f9334.jpeg"} {:name "Imperial Shuttle", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/veh_ia_1752_040381b2.jpeg"} {:name "Super Star Destroyer", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/94d4a953a3718701b8f2e30e19969dd26ccdd8df.jpg"} {:name "Sandcrawler", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_sandcrawler_01_169_55acf6cb.jpeg"} {:name "TIE Interceptor", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Tieinter2.jpg"} {:name "TIE Advanced x1", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Tiex1-headon.jpg"} {:name "X-34 Landspeeder", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/X34-landspeeder.jpg"} {:name "Speeder Bike", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_speederbike_01_169_c4204c29.jpeg"} {:name "Death Star", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Death-Star-I-copy_36ad2500.jpeg"} {:name "AT-AT Walkers", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/AT-AT_1.jpg"} {:name "Imperial Star Destroyer", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Star-Destroyer_ab6b94bb.jpeg"} {:name "X-Wing Fighter", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/X-Wing-Fighter_47c7c342.jpeg"} {:name "TIE Fighter", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/TIE-Fighter_25397c64_0.jpeg"} {:name "Slave I", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/databank_slavei_01_169_8dc3102d.jpeg"} {:name "Millennium Falcon", :image "http://origin.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/Millennium-Falcon_018ea796.jpeg"}]))


(defn cs [& args]
  (str/join " " (map name (filter identity args))))

(defn vehicle-component [vehicle]
  [:div.vehicle
   [:h3 (:name vehicle)]
   [:img {:src (:image vehicle)}]])

(defn subcomponent [txt]
  [:div.wrapper
   {:style {}
    :on-click (fn [e]
                (js/console.log "Wrapper Div"))}
   [:div.container>div.inner
    {:on-click (fn [e]
                 (.stopPropagation e)
                 (js/console.log "Inner Div.")
                 ;;(js/console.log (.-target e))
                 )}
    [:p "some content"]
    [:p txt]]])

(defn hiccup-panel []
  (let [title "Hello"
        border-color "green"
        active? true
        state (reagent/atom 0)]
    [:div#main-content.content.row
     {:class (when active? "active")
      :style {:padding-left "10%"
              :border (when border-color
                        (str "1px solid " border-color))}}
     [:h1#hiccup.big.centered "Hiccup"]
     [subcomponent "This is an argument"]
     [subcomponent "This is an argument"]
     [:p.first-paragraph.bold-text
      "This is a message"]
     [:input {:type :checkbox
              :checked false}]
     [:p "Another paragraph."]

     (doall
       (for [vehicle vehicles]
         [:div {:key (:name vehicle)}
          @state
          ^{:key (:name vehicle)} [vehicle-component vehicle]]))
     
     [:p
      {:on-click (fn [e]
                   (js/console.log "Paragraph"))}
      [:a {:href "#/"
           :on-click (fn [e]
                       (.preventDefault e)
                       (.stopPropagation e)
                       (js/console.log "Link"))}
       "Home"]]
     [:p "Some more. " [:b "And this is bold"]]]))

