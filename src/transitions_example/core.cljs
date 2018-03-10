(ns transitions-example.core
    (:require
     [cljsjs.react-transition-group]
     [transitions-example.icons :refer [chevron-left chevron-right]]
     [reagent.debug :refer [log]]
     [reagent.core :as r]))

(def Transition (r/adapt-react-class (.-Transition js/ReactTransitionGroup)))
(def TransitionGroup (r/adapt-react-class (.-TransitionGroup js/ReactTransitionGroup)))
(def CSSTransition (r/adapt-react-class (.-CSSTransition js/ReactTransitionGroup)))

(def colors ["#490A3D" "#BD1550" "#E97F02" "#F8CA00" "#8A9B0F"])

(def transitions
  {:entering-left {:opacity 0.01
                   :transform "translate(100%, 0)"}
   :entering-right {:opacity 0.01
                    :transform "translate(-100%, 0)"}

   :entered-left {:transform "translate(0, 0)"
                  :opacity 1}
   :entered-right {:transform "translate(0, 0)"
                   :opacity 1}

   :exiting-left {:transform "translate(-100%, 0)"
                  :opacity 0.01}
   :exiting-right {:transform "translate(100%, 0)"
                   :opacity 0.01}

   :exited-left {:opacity 0
                 :transform "translate(-100%, 0)"}
   :exited-right {:opacity 0
                  :transform "translate(100%, 0)"}})

(defn carousel-child
  [{:keys [direction children in]}]
   [Transition {:in in
                :timeout 500
                :unmountOnExit true}
    (fn [state]
      (let [transition (-> (str state "-")
                           (str (name direction))
                           keyword
                           transitions)]
        (r/as-element
         (into [:div {:class "child"
                      :style transition}]
               children))))])

(def reactified-child (r/reactify-component carousel-child))

(defn carousel
  [{:keys [direction]}]
  (let [children (r/children (r/current-component))
        k (-> children first meta :key)]
    [TransitionGroup {:class "parent"}
     (r/create-element reactified-child #js {:direction direction
                                             :key k
                                             :children children})]))

(defn home-page []
  (let [state (r/atom {:n 0
                       :dir :left})]
    (fn []
      [:div
       [:h3.text
        (pr-str @state)]
       [:div.container
        [:div {:on-click #(swap! state (fn [{n :n}]
                                         {:n (dec n)
                                          :dir :left}))}
         [chevron-left]]
        (let [color (->> (count colors)
                         (mod (:n @state))
                         (nth colors))]
          [:div {:class "frame"}
           [carousel {:direction (:dir @state)}
            ^{:key color}
            [:div {:style {:background-color color}
                   :class "slide"}]]])
        [:div {:on-click #(swap! state (fn [{n :n}]
                                         {:n (inc n)
                                          :dir :right}))}
         [chevron-right]]]])))

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))

(init!)
