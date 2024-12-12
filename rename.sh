for i in `find . -name "*cookiecutter*"`; do
    mv $i `echo $i | sed 's/cookiecutter/templify/'`
done
