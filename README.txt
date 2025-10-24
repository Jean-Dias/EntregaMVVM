NubankMvvm - merged project

This project merges two original projects (crypto and investments) into a single MVVM-style project.


What I did:
- Unified packages to com.example.nubankmvvm
- Created NubankDatabase (Room) with CryptoEntity, TransactionEntity, Investimento
- Added InvestimentoRepository and InvestimentoViewModel (with Factory)
- Added CryptoViewModelFactory
- Updated MainActivity to instantiate both viewmodels and pass to AppNav
- Created resource placeholders (strings/colors/ic_launcher)
- Added app/build.gradle with Compose/Room/navigation dependencies (adjust versions if needed)

Next steps you may need to do in Android Studio:
- Open project and run Gradle sync; adjust plugin versions if required by your setup
- Resolve any Compose API mismatches depending on your Kotlin/Compose versions
- Provide missing drawables or tweak themes/colors as desired
- Run and test navigation and DB behavior

If you want, I can continue iterating until Gradle build is clean — tell me to continue and I will.
