(ns understanding-re-frame.interceptors
  (:require [re-frame.core :as rf]
            [reagent.core :as reagent]))

(defonce undo-stack (reagent/atom ()))

(def undo-interceptor
  (rf/->interceptor
    :id :undo
    :before (fn [context]
              (let [db (get-in context [:coeffects :db])]
                (swap! undo-stack conj db)
                context))))

(rf/reg-event-db
  :undo
  (fn [db]
    (let [undos @undo-stack]
      (if (empty? undos)
        (do
          (js/console.log "No more undos.")
          db)
        (let [[f & rs] undos]
          (reset! undo-stack rs)
          f)))))

(defonce last-id (atom 0))

(rf/reg-cofx
  :unique-id
  (fn [cofx]
    (assoc cofx :unique-id (swap! last-id inc))))

(rf/reg-event-fx
  :add-item
  [undo-interceptor (rf/inject-cofx :unique-id)]
  (fn [cofx [_ text]]
    {:db (-> (:db cofx)
           (assoc-in [:items :by-id (:unique-id cofx)] text)
           (update-in [:items :order] (fnil conj []) (:unique-id cofx)))}))

(rf/reg-event-db
  :delete-item
  [undo-interceptor]
  (fn [db [_ id]]
    (update-in db [:items :by-id] dissoc id)))

(rf/reg-sub
  :items
  (fn [db]
    (for [id (get-in db [:items :order])
          :let [item (get-in db [:items :by-id id])]
          :when item]
      [id item])))

(defn interceptors-panel []
  (let [s (reagent/atom {})]
    (fn []
      [:div
       [:h1 "Interceptors"]
       ;;[:div (pr-str @s)]
       ;;[:div (pr-str @(rf/subscribe [:items]))]
       [:div
        (count @undo-stack)
        [:button
         {:on-click #(rf/dispatch [:undo])}
         "Undos"]]
       [:ul
        (doall
          (for [[id item] @(rf/subscribe [:items])]
            [:li {:key id}
             [:div
              item
              " "
              [:a {:href "#"
                   :on-click (fn [e]
                               (.preventDefault e)
                               (rf/dispatch [:delete-item id]))}
               "X"]]]))]
       [:form {:on-submit (fn [e]
                            (.preventDefault e)
                            (rf/dispatch [:add-item (:text @s)])
                            (swap! s assoc :text ""))}
        [:input {:type :text
                 :value (:text @s)
                 :on-change #(swap! s assoc :text
                               (-> % .-target .-value))}]]])))
