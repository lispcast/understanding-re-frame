# Understanding Re-frame

This repository is code to accompany the the [*Understanding Re-frame*](https://purelyfunctional.tv/courses/understanding-re-frame/) course on [PurelyFunctional.tv](https://purelyfunctional.tv).

## Development Mode

Clone this repository:

```bash
$CMD git clone git@github.com:lispcast/understanding-re-frame.git
```

### Start Figwheel from the command-line

Change to the directory:

```bash
$CMD cd understanding-re-frame
```

Run Figwheel:

```bash
$CMD lein figwheel
```

### Start Cider from Emacs:

Put this in your Emacs config file:

```
(setq cider-cljs-lein-repl
	"(do (require 'figwheel-sidecar.repl-api)
         (figwheel-sidecar.repl-api/start-figwheel!)
         (figwheel-sidecar.repl-api/cljs-repl))")
```

Navigate to a clojurescript file and start a figwheel REPL with `cider-jack-in-clojurescript` or (`C-c M-J`)

