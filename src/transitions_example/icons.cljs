(ns transitions-example.icons
  (:require [reagent.core :as r]))

(defn svg-icon
  [{:keys [viewbox]}]
  (into
   [:svg {:view-box viewbox
          :style {:display "inline-block"
                  :fill "#333"
                  :width "100px"
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
