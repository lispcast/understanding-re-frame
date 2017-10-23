(ns understanding-re-frame.hiccup
  (:require [reagent.core :as reagent]))

(defn hiccup-panel []
  (let [title "Hello"]
    [:div {:class "content"}
     [:h1 "Hiccup"]
     [:p "This is a message"]
     [:p "Another paragraph."]
     [:p [:a {:href "#/"} "Home"]]
     [:p "Some more. " [:b "And this is bold"]]]))

