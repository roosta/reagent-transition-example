(ns transitions-example.core
    (:require
     [cljsjs.react-transition-group]
     [transitions-example.icons :refer [star chevron-left chevron-right chevron-up chevron-down]]
     [reagent.debug :refer [log]]
     [reagent.core :as r]))

(def Transition (r/adapt-react-class (.-Transition js/ReactTransitionGroup)))
(def TransitionGroup (r/adapt-react-class (.-TransitionGroup js/ReactTransitionGroup)))
(def CSSTransition (r/adapt-react-class (.-CSSTransition js/ReactTransitionGroup)))

(def colors ["#ef3e36" "#584b53" "#2e282a" "#9d5c63" "#4c5454"])

(defn carousel-child
  [{:keys [direction children in]}]
  [CSSTransition {:in in
                  :timeout 500
                  :class-names {:enter (str "slide-enter-" (name direction))
                                :enter-active "slide-enter-active"
                                :exit "slide-exit"
                                :exit-active (str "slide-exit-active-" (name direction))
                                :exit-done "slide-exit-done"}}
   (fn [state]
     (r/as-element
      (into [:div {:class "slide-base"}] children)))])

(defn carousel
  [{:keys [direction class]}]
  (let [children (r/children (r/current-component))
        k (-> children first meta :key)]
    [TransitionGroup {:class ["transition-group" class]

                      ;; Since the direction should change for exiting children
                      ;; as well, we need to reactivly update them
                      :childFactory (fn [child]
                                      (js/React.cloneElement child #js { :direction direction}))}

     ;; to access the passed props of transition group we need to create a react
     ;; component from the carousel-child transition.
     (let [child (r/reactify-component carousel-child)]
       (r/create-element child #js {:key k
                                    :direction direction
                                    :children children}))]))

(defn on-click
  [direction]
  (fn [{n :n}]
    {:n (case direction
          :left (dec n)
          :down (dec n)
          :up (inc n)
          :right (inc n))
     :dir direction}))

(defn demo []
  (let [state (r/atom {:n 0 :dir :left})]
    (fn []
      [:div
       [:div.container
        [:div {:on-click #(swap! state (on-click :up))}
         [chevron-up]]
        [:div.row
         [:div {:on-click #(swap! state (on-click :left))}
          [chevron-left]]
         (let [color (->> (count colors)
                          (mod (:n @state))
                          (nth colors))]
           [:div {:class "frame"}
            [carousel {:direction (:dir @state)}
             ^{:key color}
             [:div {:style {:background-color color}
                    :class "slide"}]]])
         [:div {:on-click #(swap! state (on-click :right))}
          [chevron-right]]]
        [:div {:on-click #(swap! state (on-click :down))}
         [chevron-down]]]])))

(defn mount-root []
  (r/render [demo] (.getElementById js/document "app")))

(defn init! []
  (mount-root))

(init!)
