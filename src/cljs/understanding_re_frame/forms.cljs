(ns understanding-re-frame.forms
  (:require [reagent.core :as reagent]
            [clojure.string :as str]
            [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]))

(defn user-registration [defaults]
  (let [state (reagent/atom
                (merge defaults
                  {:first-name
                   {:value (:first-name defaults)}}))]
    (fn []
      [:form {:on-submit (fn [e]
                           (.preventDefault e)
                           (rf/dispatch [:form-submit @state]))}
       [:div.delete-me
        (pr-str @state)
        ]
       [:div
        (when (and (:touched? (:first-name @state))
                (not= "Dave" (:value (:first-name @state))))
          [:div.error {:style {:color (when (:changed? (:first-name @state))
                                          :red)}}
           "Hey, you're not Dave!"])
        [:label "First name"
         [:input {:name :first-name
                  :value (:value (:first-name @state))
                  :on-change (fn [e]
                               (swap! state assoc-in [:first-name :value] (-> e .-target .-value))
                               (swap! state assoc-in [:first-name :changed?] true))
                  :on-focus (fn [e]
                              (swap! state assoc-in [:first-name :touched?] true))}]]]
       [:div
        [:label "Last name"
         [:input {:name :last-name
                  :value (:last-name @state)
                  :on-change (fn [e]
                               (swap! state assoc :last-name (-> e .-target .-value)))}]]]
       [:div
        [:label "Email"
         [:input {:name :email
                  :type :email
                  :value (:email @state)
                  :on-change (fn [e]
                               (swap! state assoc :email (-> e .-target .-value)))}]]]
       [:div
        [:label "Address Line 1"
         [:input {:name :address-line-1
                  :value (:address-line-1 @state)
                  :on-change (fn [e]
                               (swap! state assoc :address-line-1 (-> e .-target .-value)))}]]]
       [:div
        [:label "Address Line 2"
         [:input {:name :address-line-2
                  :value (:address-line-2 @state)
                  :on-change (fn [e]
                               (swap! state assoc :address-line-2 (-> e .-target .-value)))}]]]
       [:div
        [:label "Zipcode"
         [:input {:name :zip-code
                  :value (:zip-code @state)
                  :on-change #(swap! state assoc :zip-code (-> % .-target .-value))}]]]
       [:button {:type :submit} "Submit"]
       ])))

(defn forms-panel []
  [:div
   [user-registration {:first-name "Eric"
                       ;;:email "eric@lispcast.com"
                       :address-line-1 "123 Pine St"
                       :address-line-2 ""
                       :zip-code "87732"}]
   ])
