import { useState } from 'react';

// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import TextField, { TextFieldProps } from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/CloseRounded';
import Typography from '@material-ui/core/Typography';

// Project components
import MarkdownRenderer from 'components/miscellaneous/MarkdownRenderer';

const useStyles = makeStyles((theme) => ({
  help: {
    color: theme.palette.text.secondary,
    cursor: 'pointer',
    textDecoration: 'underline',
  },
  dialogHeader: {
    margin: 0,
    padding: theme.spacing(2),
  },
  dialogContent: {
    padding: theme.spacing(2),
  },
  closeButton: {
    position: 'absolute',
    right: theme.spacing(1),
    top: theme.spacing(1),
    color: theme.palette.text.primary,
  },
}));

const MarkdownEditor = (props: TextFieldProps) => {
  const classes = useStyles();
  const [helpDialogOpen, setHelpDialogOpen] = useState(false);

  const HelperText = () => {
    return (
      <>
        {Boolean(props.error && props.helperText) && (
          <>
            {props.helperText}
            <br />
          </>
        )}
        <span className={classes.help} onClick={() => setHelpDialogOpen(true)}>
          Hvordan formaterer jeg teksten?
        </span>
      </>
    );
  };

  const guide = `
  *Markdown* er en vanlig måte å formatere tekst på nettet og brukes også her. Her følger en rekke eksempler på hvordan du kan legge inn overskrifter, lister, linker, bilder, osv. ved hjelp av vanlig Markdown. I tillegg kan du vise arrangement-, nyhet- og jobbannonse-kort, samt en utvid-boks.

  ___

  ## **Overskrifter**

  # Stor overskrift
  ## Mindre overskrift

  ~~~
  # Stor overskrift
  ## Mindre overskrift
  ~~~

  ___
  
  ## **Typografi**

  **Fet tekst**
  _Kursiv tekst_
  _**Fet og kursiv tekst**_

  ~~~
  **Fet tekst**
  _Kursiv tekst_
  _**Fet og kursiv tekst**_
  ~~~

  ___

  ## **Link og bilde**

  [example.com](https://example.com)

  ~~~
  Link:
  [example.com](https://example.com)

  Bilde:
  ![alternativ tekst](https://example.com/image.jpg)
  ~~~

  ___

  ## **Sitat**
  
  > Sitat som får et innrykk

  ~~~
  > Sitat som får et innrykk
  ~~~

  ___
  
  ## **Liste**

  Med tall:
  1. Første element
  2. Andre element
  3. Tredje element

  Uten tall:
  - Første element
  - Andre element
  - Tredje element

  ~~~
  Med tall:
  1. Første element
  2. Andre element
  3. Tredje element

  Uten tall:
  - Første element
  - Andre element
  - Tredje element
  ~~~

  ___

  ## **Delelinje**

  ___

  ~~~
  ___
  ~~~

  ___
  
  ## **Kodeblokk**

  \`Kodeblokk på en linje\`

  ~~~
  En linje:
  \`Kodeblokk på en linje\`

  Flere linjer:
  \`\`\`
  const party = new Party();
  party.start();

  party.stop();
  \`\`\`
  ~~~
  `;

  return (
    <>
      <TextField
        {...props}
        fullWidth
        helperText={<HelperText />}
        InputLabelProps={{ shrink: true }}
        label={props.label || 'Beskrivelse'}
        margin='normal'
        maxRows={15}
        multiline
        rows={5}
        variant={props.variant || 'outlined'}
      />
      {helpDialogOpen && (
        <Dialog aria-labelledby='format-dialog-title' fullWidth maxWidth='md' onClose={() => setHelpDialogOpen(false)} open={helpDialogOpen}>
          <DialogTitle className={classes.dialogHeader} disableTypography id='format-dialog-title'>
            <Typography variant='h3'>Formaterings-guide</Typography>
            <IconButton aria-label='close' className={classes.closeButton} onClick={() => setHelpDialogOpen(false)}>
              <CloseIcon />
            </IconButton>
          </DialogTitle>
          <DialogContent className={classes.dialogContent}>
            <MarkdownRenderer value={guide} />
          </DialogContent>
        </Dialog>
      )}
    </>
  );
};

export default MarkdownEditor;
