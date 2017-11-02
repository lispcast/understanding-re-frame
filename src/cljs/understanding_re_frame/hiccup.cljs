(ns understanding-re-frame.hiccup
  (:require [reagent.core :as reagent]
            [clojure.string :as str]))

(defn cs [& args]
  (str/join " " (map name (filter identity args))))

(defn hiccup-panel []
  (let [title "Hello"
        border-color "green"
        active? true]
    [:div {:class (cs
                    "content"
                    "row"
                    (when active? "active"))
           :style {:padding-left "10%"
                   :border (when border-color
                             (str "1px solid " border-color))}}
     [:h1 "Hiccup"]
     [:p
      {:class "first-paragraph bold-text"}
      "This is a message"]
     [:input {:type :checkbox
              :checked false}]
     [:p "Another paragraph."]
     [:p [:a {:href "#/"} "Home"]]
     [:p "Some more. " [:b "And this is bold"]]]))

