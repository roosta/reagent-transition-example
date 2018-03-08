(ns transitions-example.core
    (:require
     ["react-transition-group/TransitionGroup" :as TransitionGroup]
     ["react-transition-group/Transition" :as Transition]
     ["react-transition-group/CSSTransition" :as CSSTransition]
     [transitions-example.icons :refer [chevron-left chevron-right]]
     [reagent.core :as r]))

(def colors ["#0B486B" "#3B8686" "#79BD9A" "#A8DBA8" "#CFF09E"])

(defn on-click
  [direction]

  )

(defn home-page []
  (let [index (r/atom 0)]
    (fn []
      [:div
       [:h3.text
        "Carousel example"]
       [:div.container
        [:div {:on-click #(swap! index dec)}
         [chevron-left]]
        [:div.frame
         (let [color (->> (count colors)
                          (mod @index)
                          (nth colors))]
           [:div {:key color
                  :class "slide"
                  :style {:background-color color}}])]
        [:div {:on-click #(swap! index inc)}
         [chevron-right]]]])))

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))

(init!)
