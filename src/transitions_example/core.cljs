(ns transitions-example.core
    (:require
     [cljsjs.react-transition-group]
     [transitions-example.icons :refer [chevron-left chevron-right chevron-up chevron-down]]
     [reagent.debug :refer [log]]
     [reagent.core :as r]))

(def Transition (r/adapt-react-class (.-Transition js/ReactTransitionGroup)))
(def TransitionGroup (r/adapt-react-class (.-TransitionGroup js/ReactTransitionGroup)))
(def CSSTransition (r/adapt-react-class (.-CSSTransition js/ReactTransitionGroup)))

(def colors ["#490A3D" "#BD1550" "#E97F02" "#F8CA00" "#8A9B0F"])

(defn get-style
  [state direction]
  (merge
   (case state
     "entering" {:transform (direction {:left "translate(100%, 0)"
                                        :right "translate(-100%, 0)"
                                        :up "translate(0, 100%)"
                                        :down "translate(0, -100%)"})
                 :opacity 0.01}
     "entered" {:transform "translate(0, 0)"
                :opacity 1}
     "exiting" {:transform (direction {:left "translate(-100%, 0)"
                                       :right "translate(100%, 0)"
                                       :up "translate(0, -100%)"
                                       :down "translate(0, 100%)"})
                :opacity 0.01}
     "exited" {:opacity 0})
   {:left 0
    :top 0
    :width "100%"
    :height "100%"
    :position "absolute"
    :transition "transform 500ms ease-in-out, opacity 500ms ease-in-out"}))

(defn carousel-child
  [{:keys [direction children in]}]
   [Transition {:in in
                :timeout 500
                :unmountOnExit true}
    (fn [state]
        (r/as-element
         (into [:div {:style (get-style state direction)}]
               children)))])

(defn carousel
  [{:keys [direction class]}]
  (let [children (r/children (r/current-component))
        k (-> children first meta :key)]
    [TransitionGroup {:class class
                      :style {:position "relative"
                              :height "100%"
                              :width "100%"
                              :overflow "hidden"}

                      ;; Since the direction should change for exiting children
                      ;; as well, we need to reactivly update them
                      :childFactory (fn [child]
                                      (js/React.cloneElement child #js {:direction direction}))}

     ;; to access the passed props of transition group we need to create a react
     ;; component from the carousel-child transition.
     (let [child (r/reactify-component carousel-child)]
       (r/create-element child #js {:key k
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
  (let [state (r/atom {:n 0
                       :dir :left})]
    (fn []
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
        [chevron-down]]])))

(defn mount-root []
  (r/render [demo] (.getElementById js/document "app")))

(defn init! []
  (mount-root))

(init!)
