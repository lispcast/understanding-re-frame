(ns understanding-re-frame.hiccup
  (:require [reagent.core :as reagent]
            [clojure.string :as str]))

(defn cs [& args]
  (str/join " " (map name (filter identity args))))

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
        active? true]
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

