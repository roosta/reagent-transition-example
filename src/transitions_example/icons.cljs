(ns transitions-example.icons
  (:require [reagent.core :as r]))

(defn svg-icon
  [{:keys [viewbox class]}]
  (into
   [:svg {:view-box viewbox
          :class class
          :style {:display "inline-block"
                  :fill "#333"
                  :width "100px"
                  :cursor "pointer"
                  :height "100px"
                  :user-select "none"
                  :flex-shrink 0}}]
   (r/children (r/current-component))))

(defn chevron-right []
  [svg-icon {:viewbox "0 0 24 24"}
   [:path {:d "M8.578 16.359l4.594-4.594-4.594-4.594 1.406-1.406 6 6-6 6z"}]])

(defn chevron-left []
  [svg-icon {:viewbox "0 0 24 24"}
   [:path {:d "M15.422 16.078l-1.406 1.406-6-6 6-6 1.406 1.406-4.594 4.594z"}]])

(defn chevron-down []
  [svg-icon {:viewbox "0 0 24 24"}
   [:path {:d "M 7.41,8.295 6,9.705 l 6,6 6,-6 -1.41,-1.41 -4.59,4.58 z"}]])

(defn chevron-up []
  [svg-icon {:viewbox "0 0 24 24"}
   [:path {:d "m16.59 15.705 1.41-1.41-6-6-6 6 1.41 1.41 4.59-4.58z"}]])

(defn star [{:keys [class]}]
  [svg-icon {:viewbox "0 0 24 24"
             :class class}
   [:path {:d "M9 11.3l3.71 2.7-1.42-4.36L15 7h-4.55L9 2.5 7.55 7H3l3.71 2.64L5.29 14z"}]])
