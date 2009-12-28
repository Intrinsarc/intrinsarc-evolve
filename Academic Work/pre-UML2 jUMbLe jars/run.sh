echo running
java -cp ./jumble.jar:./jazz.jar:./nsuml1_4.jar:./nsmdf.jar:./looks-1.3-snapshot.jar:./synthetica.jar:./syntheticaGreenDream.jar:./l2fprod-common-all.jar:./vectorgraphics.jar -DtransitionsEnabled=nottrue  -Dawt.useSystemAAFontSettings=lcd -DuseSystemLookAndFeel=nottrue -DnoshowVirtualPoint -Dnoshowallpreviews -DUMLXMI1.4MetaModel="./uml14.xml" com.hopstepjump.jumble.gui.LocalApplication -DprojectDir=$1




