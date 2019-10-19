(defproject transitions-example "0.1.0-SNAPSHOT"
  :description "React-transition-group example for Reagent"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.520"]
                 [venantius/accountant "0.2.4"]
                 [secretary "1.2.3"]
                 [cljsjs/react-transition-group "4.3.0-0"]
                 [reagent "0.8.1"]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.19"]]

  :min-lein-version "2.5.0"

  :clean-targets ^{:protect false}
  [:target-path
   [:cljsbuild :builds :app :compiler :output-dir]
   [:cljsbuild :builds :app :compiler :output-to]]

  :resource-paths ["public"]

  :figwheel {:http-server-root "."
             :nrepl-port 7002
             :nrepl-middleware ["cemerick.piggieback/wrap-cljs-repl"]
             :css-dirs ["public/css"]}

  :cljsbuild {:builds {:app
                       {:source-paths ["src"]
                        :compiler
                        {:main "transitions-example.core"
                         :output-to "public/js/app.js"
                         :preloads [devtools.preload]
                         :output-dir "public/js/out"
                         :asset-path   "js/out"
                         :source-map true
                         :optimizations :none
                         :pretty-print  true}
                        :figwheel
                        {:on-jsload "transitions-example.core/mount-root"}}}}

  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.10"]
                                  [figwheel-sidecar "0.5.19"]
                                  [org.clojure/tools.nrepl "0.2.13"]
                                  [com.cemerick/piggieback "0.2.2"]]}})
