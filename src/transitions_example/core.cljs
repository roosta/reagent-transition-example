(ns transitions-example.core
    (:require
     ["react-transition-group/TransitionGroup" :as TransitionGroup]
     ["react-transition-group/Transition" :as Transition]
     ["react-transition-group/CSSTransition" :as CSSTransition]
     [transitions-example.icons :refer [chevron-left chevron-right]]
     [reagent.core :as r]))

(defonce state (r/atom {}))

(def colors ["#0B486B" "#3B8686" "#79BD9A" "#A8DBA8" "#CFF09E"])

;; -------------------------
;; Views

(defn home-page []
  [:div
   [:h3.text
    "Carousel example"]
   [:div.container
    [chevron-left]
    [:div.frame]
    [chevron-right]]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
