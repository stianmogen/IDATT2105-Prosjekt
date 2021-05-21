// Material UI Components
import { Typography } from '@material-ui/core/';

// Project Components
import Bool from 'components/inputs/Bool';
import { BuildingList } from 'types/Types';
import { useForm } from 'react-hook-form';

export type BuildingCardProps = {
  building: BuildingList;
};

type FormValues = {
  building: boolean;
};

const BuildingCard = ({ building }: BuildingCardProps) => {
  const { control, formState } = useForm<FormValues>();

  return (
    <div>
      <Bool control={control} formState={formState} name='building' type='checkbox' />
      <Typography component='h2' gutterBottom variant='h4'>
        {building.name}
      </Typography>
    </div>
  );
};

export default BuildingCard;
