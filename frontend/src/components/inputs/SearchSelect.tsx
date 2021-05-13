import { useState } from 'react';

// Material UI imports
import { makeStyles, Theme, createStyles } from '@material-ui/core/styles';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    button: {
      display: 'block',
      marginTop: theme.spacing(2),
    },
    formControl: {
      margin: theme.spacing(1),
      minWidth: 120,
    },
  }),
);

const SearchSelect = () => {
  const classes = useStyles();
  const [age, setAge] = useState<string | number>('');
  const [open, setOpen] = useState(false);

  const handleChange = (event: React.ChangeEvent<{ value: unknown }>) => {
    setAge(event.target.value as number);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleOpen = () => {
    setOpen(true);
  };

  return (
    <div>
      <FormControl className={classes.formControl} fullWidth>
        <InputLabel id='demo-controlled-open-select-label'>Building</InputLabel>
        <Select
          id='demo-controlled-open-select'
          labelId='demo-controlled-open-select-label'
          onChange={handleChange}
          onClose={handleClose}
          onOpen={handleOpen}
          open={open}
          value={age}>
          <MenuItem value=''>
            <em>None</em>
          </MenuItem>
          <MenuItem value={10}>Ten</MenuItem>
          <MenuItem value={20}>Twenty</MenuItem>
          <MenuItem value={30}>Thirty</MenuItem>
        </Select>
      </FormControl>
    </div>
  );
};

export default SearchSelect;
