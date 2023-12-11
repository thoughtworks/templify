# for s in `git diff --name-only HEAD~`
for s in `git diff --name-only`
do
if [[ $s == *".java" ]]; then
  s=$(echo $s | tr '/' '.')
  s=$(echo ${s%.java})
  s=${s/.java/}
  if [[ $s != *"Test.java" ]]; then
    s=${s/src./}Test
  fi
  s=$(echo "$s" | sed 's/.*\.//')
  classes=$classes,$s
fi
done
echo "Test Suite: " ${classes:1}
mvn test -Dtest=${classes:1} -DunitOnly=true
