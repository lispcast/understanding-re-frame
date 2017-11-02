(ns understanding-re-frame.hiccup
  (:require [reagent.core :as reagent]))

(defn hiccup-panel []
  (let [title "Hello"
        border-color "green"]
    [:div {:class "content"
           :style {:padding-left "10%"
                   :border (when border-color
                             (str "1px solid " border-color))}}
     [:h1 "Hiccup"]
     [:p "This is a message"]
     [:input {:type :checkbox
              :checked false}]
     [:p "Another paragraph."]
     [:p [:a {:href "#/"} "Home"]]
     [:p "Some more. " [:b "And this is bold"]]]))

