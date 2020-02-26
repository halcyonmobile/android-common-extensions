## Halcyon Mobile Common Android Extensions

This repository is an aggregation of common Android extensions we are using at Halcyon Mobile.


### Safe Navigation

Safe navigation is a wrapper around the default NavController which checks the currentDestinationId.
The reasoning is when a user clicks on two or more CTAs which result in navigation that can crash the application.
That's because the second NavigationEvent won't be understood by the NavController since the first NavigationEvent changed it's currentDestination.

#### How do I setup?

```gradle
implementation "com.halcyonmobile.android.common.extensions:safe.navigation:<latest-version>"
```

#### How do I use it?

```kotlin

Fragment.findSafeNavController().navigate(FooDirections.actionFooToBar())

```

#### Notes
For any other action simply used the default NavController via Fragment.findNavController()



<h1 id="license">License :page_facing_up:</h1>

Copyright (c) 2020 Halcyon Mobile.
> https://www.halcyonmobile.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

> http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.