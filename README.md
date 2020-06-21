### Description

NOTE: Updated by R. Berger from the original implementation of Daniel Berg
(roosta) so that it can use `react` and `react-transition-group` libraries from
npmjs.com via shadow-cljs instead of requiring cljsjs libraries and figwheel.
It also switches from lein (project.clj) to clojure tools (deps.edn).

The rest of the readme is from the original other than replacing figwheel with shadow-cljs

----

An problem I've had multiple times when creating
[Reagent](https://github.com/reagent-project/reagent) apps is that I'd like to
transition something in and out depending on their mount status. A router for
example, that can transition pages in and out, or an image slideshow where you'd
simply change the source of the image. Ever since
[react-transition-group](https://github.com/reactjs/react-transition-group)
updated to API v2 all my existing solutions for this problem failed. I made this
example for reference using the new API.

### Development mode

To start shadow-cljs watch; navigate to the project folder and run the following command in the terminal:

```
yarn
shadow-cljs -A:dev watch app

```

You only need to run yarn again if you changed the package.json

Shadow-cljs will automatically push cljs changes to the browser. Once
shadow-cljs starts up, you should be able to open the `public/index.html` page
in the browser by accessing http://localhost:3000

### Resources
- [Dynamic transitions with react-router and react-transition-group](https://medium.com/lalilo/dynamic-transitions-with-react-router-and-react-transition-group-69ab795815c9)
- [React Transition Group](https://reactcommunity.org/react-transition-group/)
- [reagent/InteropWithReact.md at master · reagent-project/reagent](https://github.com/reagent-project/reagent/blob/master/docs/InteropWithReact.md)
