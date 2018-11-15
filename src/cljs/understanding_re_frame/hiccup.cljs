(ns understanding-re-frame.hiccup
  (:require [reagent.core :as reagent]
            [clojure.string :as str]
            [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]))

(rf/reg-event-fx
 :save-image
 (fn [cofx [_ form-data]]
   {:http-xhrio {:uri "https://whispering-cove-34851.herokuapp.com/avatar"
                 :body form-data
                 :method :post
                 :timeout 10000
                 :response-format (ajax/json-response-format {:keywords? true})}}))

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

(def html "<ul><li><b>Some HTML.</b></li><li>Another list item.</li></ul>")

(defn hiccup-panel []
  (let [title "Hello"
        border-color "green"
        active? true
        state (reagent/atom 0)
        refs (reagent/atom {})]
    (fn []
      [:div#main-content.content.row
       {:class (when active? "active")
        :style {:padding-left "10%"
                :border (when border-color
                          (str "1px solid " border-color))}}
       [:h1#hiccup.big.centered "Hiccup"]
       [:div {:dangerouslySetInnerHTML {:__html html}}]
       [subcomponent "This is an argument "]
       [subcomponent "This is an argument"]
       [:p.first-paragraph.bold-text
        "This is a message"]
       [:input {:type :checkbox
                :checked false}]
       [:p "Another paragraph."]
       [:div.inner
        [:form {:ref #(swap! refs assoc :form %)
                :on-submit (fn [e]
                             (.preventDefault e))}
         [:input {:type :file
                  :name :image
                  :on-change (fn [e]
                               (.preventDefault e)
                               (js/console.log (:form @refs))
                               (rf/dispatch [:save-image
                                             (js/FormData.
                                               (:form @refs))]))}
          ]]
        ]
       [:p
        {:on-click (fn [e]
                     (js/console.log "Paragraph"))}
        [:a {:href "#/"
             :on-click (fn [e]
                         (.preventDefault e)
                         (.stopPropagation e)
                         (js/console.log "Link"))}
         "Home"]]
       [:p "Some more. " [:b "And this is bold"]]]
      )))

