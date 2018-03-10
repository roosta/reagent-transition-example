(ns transitions-example.core
    (:require
     [cljsjs.react-transition-group]
     [transitions-example.icons :refer [chevron-left chevron-right]]
     [reagent.debug :refer [log]]
     [reagent.core :as r]))

;; ["react-transition-group/TransitionGroup" :as TransitionGroup]
;; ["react-transition-group/Transition" :as Transition]
;; ["react-transition-group/CSSTransition" :as CSSTransition]
(def Transition (r/adapt-react-class (.-Transition js/ReactTransitionGroup)))
(def TransitionGroup (r/adapt-react-class (.-TransitionGroup js/ReactTransitionGroup)))
(def CSSTransition (r/adapt-react-class (.-CSSTransition js/ReactTransitionGroup)))
(def colors ["#0B486B" "#3B8686" "#79BD9A" "#A8DBA8" "#CFF09E"])

(def transitions
  {:enter {:opacity 0.01
           }
   ;; :enter-right {:opacity 0.01
   ;;               :transform "translate(-100%, 0)"}
   :entered {:transform "translate(0, 0)"
             :opacity 1}
   :exit {:transform "translate(-100%, 0)"
           :opacity 0}
   ;; :leave-right {:transform "translate(100%, 0)"
   ;;               :opacity 0}
   :exited {:transform "translate(-100%, 0)"
                       :opacity 0.01}
   ;; :leave-active-right {:transform "translate(100%, 0)"
   ;;                      :opacity 0.01}
   })

(defn slide
  [state color]
  [:div {:class "slide"
         :style {:background-color color}}])

(defn transition
  [props]
  (let [js-props (clj->js props)
        this (r/current-component)
        children (r/children this)
        in-prop (.-in js-props)
        color (.-color js-props)]
    (into
     [CSSTransition {:timeout 500
                     :classNames "slide"
                     :key color
                     :in in-prop}]
     children)))

(defn carousel-child
  "wrapper class for children passed to carousel parent.
    Needed to add a style for children hence the second wrapper"
  [{:keys [color direction children in]}]
  (into [CSSTransition {:key color
                        :in in
                        :timeout 500
                        :classNames {:enter (str "enter-" (name direction))
                                     :enterActive "enter-active"
                                     :exit (str "exit-" (name direction))
                                     :exitActive (str "exit-active-" (name direction))}
                        :class "child"}]
        children))

(def reactified-child (r/reactify-component carousel-child))

(defn carousel
  [{:keys [direction color]}]
  [TransitionGroup {:class "parent"}
   (r/create-element reactified-child #js {:direction direction
                                           :color color
                                           :children (r/children (r/current-component))})]


  #_(map (fn [child]
           (let [k (or (:key (second child))
                       (:key (meta child)))]
             (assert k "You need to provide a key for child elements")
             [carousel-child {:key k
                              :direction direction}
              child]))
         (r/children (r/current-component))))

(defn home-page []
  (let [state (r/atom {:n 0
                       :dir :left})]
    (fn []
      [:div
       [:h3.text
        "Carousel example"]
       [:div.container
        [:div {:on-click #(swap! state (fn [{n :n}]
                                         {:n (dec n)
                                          :dir :left}))}
         [chevron-left]]
        (let [color (->> (count colors)
                         (mod (:n @state))
                         (nth colors))]
          [:div {:class "frame"}
           [carousel {:direction (:dir @state)
                      :color color}
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
#_[:div {:class "slide"
       :style {:background-color color}}]
