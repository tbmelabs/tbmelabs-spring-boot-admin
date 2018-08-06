# angular / universal / socket-engine

This vendor packages mirror the repository at [commit ea6f7b13b70c40b55ce72bd549e2dfb56ca146db](https://github.com/angular/universal/commit/ea6f7b13b70c40b55ce72bd549e2dfb56ca146db). Local paths (imports) were adapted accordingly.

## Bugs and replacement

The engine is still experimental and contains some bugs. The [major bug]([https://github.com/angular/universal/pull/1003]) depends on (bazel)[https://github.com/bazelbuild/rules_typescript/issues/211]. Once the PR is merged you could install those libraries via NPM.

## Future installation

Once the module is merged you can install it as following: `npm install @nguniversal/socket-engine @nguniversal/common --save`
