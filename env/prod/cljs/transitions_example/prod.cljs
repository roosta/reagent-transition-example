(ns transitions-example.prod
  (:require
    [transitions-example.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
