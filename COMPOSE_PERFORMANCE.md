# Compose performance

Since the compose tooling isn't perfect one might want to investigate performance issues manually. There are some tools to help us with that.

## Standard performance checklist

There is official guideline on how to improve compose performance. [Read it here](https://developer.android.com/jetpack/compose/performance).

## Stability investigation

A lot of performance issues in Compose is related to stability issues. If classes or composables are not inferred as stable there might be a penalty issues.
There are a lot of [great articles](https://medium.com/androiddevelopers/jetpack-compose-stability-explained-79c10db270c8) about that as well as [official compiler metrics documentation](https://github.com/androidx/androidx/blob/androidx-main/compose/compiler/design/compiler-metrics.md).

In this repository compiler metrics are coocked in. To generate reports you need to set a compiler flag properly and make sure
all tasks are re-run. For example, to generate metrics for whole project you have to run:

> gradle assembleRelease -PenableComposeCompilerReports=true --rerun-tasks --no-build-cache

Reports are generated to build directories of respective modules. So for `weather` module the metrics are generated to

> ./features/weather/impl/build/compose_metrics

You can converts those metrics to a more readable HTML format using [this tool](https://github.com/PatilShreyas/Compose-report-to-HTML).
